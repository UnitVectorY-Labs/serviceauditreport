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
package com.unitvectory.serviceauditreport.analyzer.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.unitvectory.serviceauditreport.core.app.service.AbstractAppService;
import com.unitvectory.serviceauditreport.core.model.AbstractConfig;

import lombok.AllArgsConstructor;

/**
 * The Collector Service
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Service
@AllArgsConstructor
public class AnalyzerService extends AbstractAppService {

    private static Logger LOG = LoggerFactory.getLogger(AnalyzerService.class);

    private ApplicationContext applicationContext;

    @Override
    public void run(AbstractConfig config) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}