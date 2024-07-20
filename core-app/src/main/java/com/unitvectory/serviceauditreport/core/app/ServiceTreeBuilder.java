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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;

/**
 * The Service Tree Builder
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
class ServiceTreeBuilder {

    /**
     * Build the service tree which is a map with nodes that represent the
     * relationships between services
     * 
     * @param services the services
     * @return the service tree
     */
    public static Map<Class<?>, ServiceTreeNode> buildServiceTree(List<AbstractService<?, ?>> services) {
        Map<Class<?>, ServiceTreeNode> nodeMap = new HashMap<>();

        // Create nodes for all services
        for (AbstractService<?, ?> service : services) {
            ServiceTreeNode node = new ServiceTreeNode(service);
            nodeMap.put(node.getOutputClass(), node);
        }

        // Link parent and child nodes
        for (ServiceTreeNode node : nodeMap.values()) {
            if (node.getParentClass() != null && nodeMap.containsKey(node.getParentClass())) {
                ServiceTreeNode parentNode = nodeMap.get(node.getParentClass());

                // Build the bi-directional tree structure
                parentNode.getChildren().add(node);
                node.setParentNode(parentNode);
            }
        }

        return nodeMap;
    }
}
