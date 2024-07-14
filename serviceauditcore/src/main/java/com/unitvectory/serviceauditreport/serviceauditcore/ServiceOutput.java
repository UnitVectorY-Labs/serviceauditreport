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

import java.util.ArrayList;
import java.util.List;

/**
 * The service output class.
 * 
 * @param <O> the output type of the service
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class ServiceOutput<O> {

    /**
     * The class of the output.
     */
    private Class<O> clazz;

    /**
     * The output list.
     */
    private final List<O> output;

    /**
     * Creates a new service output.
     */
    public ServiceOutput(Class<O> clazz) {
        this.clazz = clazz;
        this.output = new ArrayList<>();
    }

    /**
     * Gets the output.
     * 
     * @param item the item to add
     * @return the output
     */
    public ServiceOutput<O> add(O item) {
        this.output.add(item);
        return this;
    }

    /**
     * Saves the output.
     * @param dataManager the data manager
     */
    public void save(DataManagerHierarchical dataManager) {
        dataManager.save(clazz, output);
    }
}
