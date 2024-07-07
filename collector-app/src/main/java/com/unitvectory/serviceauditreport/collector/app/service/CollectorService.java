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
package com.unitvectory.serviceauditreport.collector.app.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.unitvectory.serviceauditreport.collector.AbstractCollectorTask;
import com.unitvectory.serviceauditreport.collector.CollectorDataContext;
import com.unitvectory.serviceauditreport.core.app.service.AbstractAppService;
import com.unitvectory.serviceauditreport.core.model.AbstractConfig;
import com.unitvectory.serviceauditreport.core.persistence.AbstractPersistenceService;

import lombok.AllArgsConstructor;

/**
 * The Collector Service
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Service
@AllArgsConstructor
public class CollectorService extends AbstractAppService {

    private static Logger LOG = LoggerFactory.getLogger(CollectorService.class);

    private ApplicationContext applicationContext;

    private AbstractPersistenceService persistenceService;

    @Override
    public void run(AbstractConfig config) {
        LOG.info("Loading collectors to run...");
        Map<String, AbstractCollectorTask> beansOfType = applicationContext.getBeansOfType(AbstractCollectorTask.class,
                true, true);

                CollectorDataContext collectorDataContext = new CollectorDataContext(persistenceService);

        for (Map.Entry<String, AbstractCollectorTask> entry : beansOfType.entrySet()) {
            AbstractCollectorTask collector = entry.getValue();
            System.out.println("Bean name: " + entry.getKey() + ", Bean instance: " + entry.getValue());

            // collector.execute(null);
        }

        LOG.info("Data collection finished.");
    }
}
