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

/**
 * The abstract service class.
 * 
 * @param <O> the output of the service
 * @param <C> the configuration type of the service
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public abstract class AbstractService<O, C> {

    /**
     * Executes the logic for the service to collect the required data
     * 
     * @param dataManager   the data manager, used to look up data
     * @param configuration the configuration for this specific service
     * @return the output of the service
     */
    public abstract ServiceOutput<O> execute(DataManagerRead dataManager, C configuration);

    /**
     * Get the output class for this service
     * @return the output class
     */
    public abstract Class<O> getOutputClass();

    /**
     * Get the configuration class for this service
     * @return the configuration class
     */
    public abstract Class<C> getConfigurationClass();
}
