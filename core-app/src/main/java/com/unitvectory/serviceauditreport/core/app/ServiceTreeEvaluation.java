/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.unitvectory.serviceauditreport.core.app;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pmw.tinylog.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.unitvectory.serviceauditreport.core.app.data.FileDataManager;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.AnnotationUtils;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerHierarchicalDecorator;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;
import com.unitvectory.serviceauditreport.serviceauditcore.JacksonObjectMapper;
import com.unitvectory.serviceauditreport.serviceauditcore.ParentIdentifier;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceAuditReportException;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceOutput;

/**
 * The Service Tree Evaluation
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
class ServiceTreeEvaluation {

    private final Map<Class<?>, Object> configMap;

    private final JsonNode rootConfiguration;

    /**
     * Create a new Service Tree Evaluation
     * 
     * @param rootConfiguration the root configuration
     */
    public ServiceTreeEvaluation(JsonNode rootConfiguration) {
        this.configMap = new HashMap<>();
        this.rootConfiguration = rootConfiguration;
    }

    private <C> C getConfiguration(Class<C> configurationClass) {
        Object config = configMap.get(configurationClass);
        if (config != null) {
            return configurationClass.cast(config);
        } else {
            // Load the configuration
            String name = AnnotationUtils.getConfigName(configurationClass);
            Logger.info("Loading config for '" + name + "'");
            JsonNode configJson = rootConfiguration.get(name);
            if (configJson == null) {
                throw new ServiceAuditReportException("Configuration not found for " + name);
            }

            // Parse the configuration
            config = JacksonObjectMapper.OBJECT_MAPPER.convertValue(configJson, configurationClass);
            configMap.put(configurationClass, config);
            return configurationClass.cast(config);
        }
    }

    /**
     * Evaluate the services
     * 
     * @param fileDataManager the file data manager
     * @param services        the services
     */
    public void evaluate(FileDataManager fileDataManager, List<AbstractService<?, ?>> services) {

        Logger.info("Starting...");

        // Reverse the queue
        Collections.reverse(services);

        // Get all of the services that need to be evaluated in a convenient tree
        // structure
        Map<Class<?>, ServiceTreeNode> tree = ServiceTreeBuilder.buildServiceTree(services);

        // We need to keep track of which services have been evaluated
        Set<Class<?>> evaluated = new HashSet<>();

        int totalCount = tree.size();
        int evaluatedCount = 0;

        while (evaluatedCount < totalCount) {
            int roundEvaluatedCount = 0;
            for (Entry<Class<?>, ServiceTreeNode> entry : tree.entrySet()) {
                ServiceTreeNode node = entry.getValue();

                if (node.isEvaluated()) {
                    continue;
                }

                Logger.info("Evaluating: " + node.getService().getClass().getName());

                if (node.canEvaluate(evaluated)) {
                    AbstractService<?, ?> service = node.getService();
                    Logger.info("Can evaluate: " + service.getClass().getName());

                    // Get the configuration class
                    Object config = this.getConfiguration(service.getConfigurationClass());

                    List<ParentIdentifier> parentIdentifiers = new ArrayList<>();
                    if (node.getParentClass() == Void.class) {
                        parentIdentifiers.add(null);
                    } else {
                        ServiceTreeNode parentNode = tree.get(node.getParentClass());
                        if (parentNode == null) {
                            throw new ServiceAuditReportException("Parent node not found: " + node.getParentClass());
                        }
                        parentIdentifiers = parentNode.getParentIdentifiers();
                    }

                    for (ParentIdentifier parentIdentifier : parentIdentifiers) {
                        DataManagerHierarchicalDecorator decorator = new DataManagerHierarchicalDecorator(
                                fileDataManager,
                                parentIdentifier);

                        ServiceOutput<?> output = null;
                        try {
                            // This is tricky, we have an abstract class with generics that we want to be
                            // able to call a specific method for passing in the configuration
                            Method executeMethod = service.getClass().getMethod("execute", DataManagerRead.class,
                                    config.getClass());
                            output = (ServiceOutput<?>) executeMethod.invoke(service, decorator, config);
                        } catch (Exception e) {
                            Logger.error("Failed to execute service: " + e.getMessage());
                            throw new RuntimeException(e);
                        }

                        Logger.info("Output:");
                        for (Object obj : output.getOutput()) {
                            Logger.info(obj.toString());

                            ParentIdentifier newParentIdentifier = ParentIdentifier.of(obj, parentIdentifier);
                            node.addParentIdentifier(newParentIdentifier);

                            try {
                                // This is tricky, we have an abstract class with generics that we want to be
                                // able to call a specific method so reflection is needed
                                Method executeMethod = decorator.getClass().getDeclaredMethod("save",
                                        Class.class, Object.class, ParentIdentifier.class);
                                executeMethod.invoke(decorator, obj.getClass(), obj, parentIdentifier);
                            } catch (Exception e) {
                                Logger.error("Failed to execute service: " + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    node.setEvaluated(true);
                    evaluated.add(node.getOutputClass());
                    evaluatedCount++;
                    roundEvaluatedCount++;
                }
            }

            if (roundEvaluatedCount == 0) {
                Logger.error("Failed to evaluate any services.  This is likely due to a circular dependency.");
                break;
            }
        }

        Logger.info("Finished.");
    }
}
