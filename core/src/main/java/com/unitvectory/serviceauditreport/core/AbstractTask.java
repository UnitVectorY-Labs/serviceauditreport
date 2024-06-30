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
package com.unitvectory.serviceauditreport.core;

/**
 * An abstract task that
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public interface AbstractTask {

    /**
     * Execute the task
     * 
     * @param dataContext the data context for retreiving data
     * @return the data product
     */
    DataProduct execute(DataContext dataContext);

    /**
     * Get the input types
     * 
     * @return the input types
     */
    TaskInputTypes getInputTypes();

    /**
     * Get the output types
     * 
     * @return the output types
     */
    TaskOutputTypes getOutputTypes();
}
