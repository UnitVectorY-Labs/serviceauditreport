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

/**
 * The DataManagerHierarchical interface.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public interface DataManagerHierarchical extends DataManager {

    /**
     * Loads the instances of the class.
     * 
     * @param <T>   the class type
     * @param clazz the class type
     * @return the instances of the class
     */
    default <T> List<T> load(Class<T> clazz) {
        return load(clazz, null);
    }

    /**
     * Loads the instances of the class.
     * 
     * @param <T>              the class type
     * @param clazz            the class type
     * @param parentIdentifier the parent identifier for where to load the instances
     * @return the instances of the class
     */
    <T> List<T> load(Class<T> clazz, ParentIdentifier parentIdentifier);

    /**
     * Saves the instance of the class.
     * 
     * @param <T>      the class type
     * @param clazz    the class type
     * @param instance the instance of the class
     */
    default <T> void save(Class<T> clazz, T instance) {
        save(clazz, instance, null);
    }

    /**
     * Saves the instance of the class.
     * 
     * @param <T>              the class type
     * @param clazz            the class type
     * @param instance         the instance of the class
     * @param parentIdentifier the parent identifier for where to save the instance
     */
    <T> void save(Class<T> clazz, T instance, ParentIdentifier parentIdentifier);
}