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
package com.unitvectory.serviceauditreport.service.demo.collector;

import com.unitvectory.serviceauditreport.collector.AbstractCollectorTask;
import com.unitvectory.serviceauditreport.collector.CollectorDataContext;
import com.unitvectory.serviceauditreport.collector.CollectorDataProduct;
import com.unitvectory.serviceauditreport.core.TaskInputTypes;
import com.unitvectory.serviceauditreport.core.TaskOutputTypes;
import com.unitvectory.serviceauditreport.core.persistence.AbstractPersistenceService;
import com.unitvectory.serviceauditreport.service.demo.model.DemoData;
import com.unitvectory.serviceauditreport.service.demo.model.DemoDataPoint;

/**
 * The Demo collector
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class DemoCollector extends AbstractCollectorTask {

    public DemoCollector(AbstractPersistenceService persistenceService) {
        super(TaskInputTypes.builder().build(), TaskOutputTypes.builder().type(DemoData.class).build(), persistenceService);
    }

    @Override
    public CollectorDataProduct executeCollector(CollectorDataContext dataContext) {

        CollectorDataProduct product = new CollectorDataProduct();

        DemoData demoData = new DemoData();

        // Generate 20 ids and numbers as data points
        for (int i = 0; i < 20; i++) {
            // TODO: Make this data random
            DemoDataPoint dataPoint = DemoDataPoint.builder()
                    .id("id-" + i)
                    .number(i)
                    .build();
            demoData.getData().add(dataPoint);
        }

        product.setSingularData(DemoData.class, demoData);

        return product;
    }
}
