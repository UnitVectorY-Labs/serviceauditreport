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

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.AccessType;
import com.unitvectory.serviceauditreport.serviceauditcore.AnnotationUtils;
import com.unitvectory.serviceauditreport.serviceauditcore.DataEntity;
import com.unitvectory.serviceauditreport.serviceauditcore.ParentIdentifier;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceInput;

/**
 * The Service Tree Node
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Getter
@Setter
@RequiredArgsConstructor
class ServiceTreeNode {
    @NonNull
    private final AbstractService<?, ?> service;

    @NonNull
    private final Class<?> outputClass;

    @NonNull
    private final AccessType accessType;

    @NonNull
    private final Class<?> parentClass;

    @NonNull
    private final List<Class<?>> relatives;

    private ServiceTreeNode parentNode;
    private final List<ServiceTreeNode> children = new ArrayList<>();

    private boolean evaluated = false;
    private final List<ParentIdentifier> parentIdentifiers = new ArrayList<>();

    /**
     * Create a new service tree node
     * 
     * @param service the service
     */
    public ServiceTreeNode(AbstractService<?, ?> service) {
        this.service = service;
        this.outputClass = service.getOutputClass();

        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(this.outputClass);
        this.accessType = dataEntity.accessType();

        ServiceInput serviceInput = AnnotationUtils.getServiceInput(service.getClass());
        this.parentClass = serviceInput.parent();
        this.relatives = List.of(serviceInput.relatives());
    }

    /**
     * Add a parent identifier
     * 
     * @param parentIdentifier the parent identifier
     */
    public void addParentIdentifier(ParentIdentifier parentIdentifier) {
        this.parentIdentifiers.add(parentIdentifier);
    }

    /**
     * Check if this is the root node
     * 
     * @return true if this is the root node
     */
    public boolean isRoot() {
        return this.parentClass == null;
    }

    /**
     * Check if this node can be evaluated
     * 
     * @param evaluated the evaluated nodes
     * @return true if this node can be evaluated
     */
    public boolean canEvaluate(Set<Class<?>> evaluated) {
        if (this.parentClass != Void.class && !evaluated.contains(this.parentClass)) {
            return false;
        } else if (this.relatives != null) {
            for (Class<?> relative : this.relatives) {
                if (!evaluated.contains(relative)) {
                    return false;
                }
            }
        }

        return true;
    }
}
