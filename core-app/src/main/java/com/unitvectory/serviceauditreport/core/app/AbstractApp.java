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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.pmw.tinylog.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.unitvectory.serviceauditreport.core.app.data.FileDataManager;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.AnnotationUtils;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;
import com.unitvectory.serviceauditreport.serviceauditcore.JacksonObjectMapper;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceOutput;

/**
 * The Abstract App
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public abstract class AbstractApp {

    /**
     * The application name
     * 
     * @return the application name
     */
    protected abstract String getAppName();

    /**
     * The service annotation class
     * 
     * @return the service annotation class
     */
    protected abstract Class<?> getServiceAnnotationClass();

    /**
     * Run the application
     * 
     * @param args the arguments
     */
    protected void run(String... args) {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        try {
            CommandLine line = parser.parse(options, args);

            String configPath = line.getOptionValue("config");

            // TODO: pass in path to store files as another parameter
            File root = new File("data");

            FileDataManager fileDataManager = new FileDataManager(root);

            File file = new File(configPath);
            if (!file.exists()) {
                Logger.error("The configuration file does not exist: " + configPath);
                return;
            }

            JsonNode rootNode = JacksonObjectMapper.OBJECT_MAPPER.readTree(file);

            // Loop through all of the nodes getting the key and value as JsonNode
            rootNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();

                process(fileDataManager, key, value);
            });

            // TODO: Everything

        } catch (ParseException exp) {
            handleParseException(exp, options);
        } catch (IOException e) {
            Logger.error("Failed to read the configuration file: " + e.getMessage());
        }
    }

    private void process(FileDataManager fileDataManager, String configPackage, JsonNode configJson) {

        List<AbstractService<?, ?>> services = AnnotationUtils.getAnnotatedServices(configPackage,
                getServiceAnnotationClass());

        Map<Class<?>, Object> configurationMap = new HashMap<>();

        // TODO: This needs to execute the services in the correct order evaluating the
        // tree and passing in the correct parents. The current method of just looping
        // through does not work correctly and none of the parent relationships are
        // being handled at all.

        for (AbstractService<?, ?> service : services) {

            Class<?> configClass = service.getConfigurationClass();
            Object config = configurationMap.get(configClass);
            if (config == null) {
                config = JacksonObjectMapper.OBJECT_MAPPER.convertValue(configJson, configClass);
                configurationMap.put(configClass, config);
            }

            ServiceOutput<?> output = null;
            try {
                // This is tricky, we have an abstract class with generics that we want to be
                // able to call a specific method for passing in the configuration
                Method executeMethod = service.getClass().getMethod("execute", DataManagerRead.class,
                        config.getClass());
                output = (ServiceOutput<?>) executeMethod.invoke(service, fileDataManager, config);
            } catch (Exception e) {
                Logger.error("Failed to execute service: " + e.getMessage());
                throw new RuntimeException(e);
            }

            // TODO: Fully handle the output
            output.save(fileDataManager);
        }

    }

    /**
     * Create the options
     * 
     * @return the options
     */
    private Options createOptions() {
        Options options = new Options();

        Option configOption = Option.builder("c")
                .longOpt("config")
                .argName("config")
                .desc("the " + this.getAppName() + " configuration")
                .hasArg()
                .required(true)
                .build();

        options.addOption(configOption);
        return options;
    }

    /**
     * Handle the parse exception
     * 
     * @param exp     the exception
     * @param options the options
     */
    private void handleParseException(ParseException exp, Options options) {
        System.out.println("Failed to parse command line arguments: " + exp.getMessage());
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(this.getAppName() + " -c config.json", options);
    }
}
