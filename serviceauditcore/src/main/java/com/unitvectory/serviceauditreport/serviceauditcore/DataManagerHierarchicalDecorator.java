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
package com.unitvectory.serviceauditreport.serviceauditcore;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * The DataManagerHierarchicalDecorator
 * 
 * A decerator for a DataManagerHierarchical that always passes the provided
 * ParentIdentifier allowing a client to interact with just the DataManager.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor
public class DataManagerHierarchicalDecorator implements DataManagerHierarchical {

    /**
     * The data manager that is being decorated
     */
    private final DataManagerHierarchical dataManager;

    /**
     * The parent identifier that is passed to the data manager for all requests
     * transparently to simplify the use by the service.
     */
    private final ParentIdentifier parentIdentifier;

    @Override
    public <T> List<T> load(Class<T> clazz) {
        return this.dataManager.load(clazz, this.parentIdentifier);
    }

    @Override
    public <T> List<T> load(Class<T> clazz, ParentIdentifier parentIdentifier) {
        return this.dataManager.load(clazz, this.parentIdentifier);
    }

    @Override
    public <T> void save(Class<T> clazz, T instance) {
        this.dataManager.save(clazz, instance, this.parentIdentifier);
    }

    @Override
    public <T> void save(Class<T> clazz, T instance, ParentIdentifier parentIdentifier) {
        this.dataManager.save(clazz, instance, this.parentIdentifier);
    }

    @Override
    public <T> void save(Class<T> clazz, List<T> instances) {
        this.dataManager.save(clazz, instances, this.parentIdentifier);
    }

    @Override
    public <T> void save(Class<T> clazz, List<T> instances, ParentIdentifier parentIdentifier) {
        this.dataManager.save(clazz, instances, this.parentIdentifier);
    }
}
