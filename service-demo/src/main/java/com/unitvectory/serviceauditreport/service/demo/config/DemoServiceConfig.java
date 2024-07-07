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
package com.unitvectory.serviceauditreport.service.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.unitvectory.serviceauditreport.core.persistence.AbstractPersistenceService;
import com.unitvectory.serviceauditreport.service.demo.collector.DemoCollector;

import lombok.AllArgsConstructor;

/**
 * The DemoServiceConfig
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Configuration
@AllArgsConstructor
public class DemoServiceConfig {

    private AbstractPersistenceService persistenceService;

    @Bean
    public DemoCollector demoCollector() {
        return new DemoCollector(persistenceService);
    }
}