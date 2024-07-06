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
package com.unitvectory.serviceauditreport.core.persistence;

/**
 * The AbstractPersistenceService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public abstract class AbstractPersistenceService {


    /**
     * Save the data
     * 
     * @param data the data to save
     * @param file the file to save the data
     * @param path the path to save the data
     */
    public abstract void saveData(Object data, String file, String... path);

    /**
     * Load the data
     * 
     * @param clazz the class to load
     * @param file the file to load the data
     * @param path the path to load the data
     * @return the data
     */
    public abstract <T> T loadData(Class<T> clazz, String file, String... path);
}
