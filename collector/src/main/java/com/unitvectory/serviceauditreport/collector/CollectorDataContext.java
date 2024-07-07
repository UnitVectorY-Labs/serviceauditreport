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
package com.unitvectory.serviceauditreport.collector;

import java.util.List;

import com.unitvectory.serviceauditreport.core.AbstractData;
import com.unitvectory.serviceauditreport.core.DataContext;
import com.unitvectory.serviceauditreport.core.persistence.AbstractPersistenceService;

import lombok.AllArgsConstructor;

/**
 * The collector data context
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor
public class CollectorDataContext implements DataContext {

    private final AbstractPersistenceService persistenceService;

    @Override
    public <T extends AbstractData> T getSingularData(Class<T> type) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSingularData'");
    }

    @Override
    public <T extends AbstractData> List<T> getAllData(Class<T> type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllData'");
    }

}
