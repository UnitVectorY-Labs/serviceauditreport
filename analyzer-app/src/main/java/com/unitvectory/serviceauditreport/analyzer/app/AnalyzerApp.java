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
package com.unitvectory.serviceauditreport.analyzer.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unitvectory.serviceauditreport.analyzer.app.service.AnalyzerService;
import com.unitvectory.serviceauditreport.core.app.AbstractApp;
import com.unitvectory.serviceauditreport.core.app.service.AbstractAppService;

import lombok.AllArgsConstructor;

/**
 * The Analyzer App
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@SpringBootApplication(scanBasePackages = "com.unitvectory.serviceauditreport")
@AllArgsConstructor
public class AnalyzerApp extends AbstractApp {

    private static Logger LOG = LoggerFactory.getLogger(AnalyzerApp.class);

    private AnalyzerService analyzerService;

    @Override
    protected String getAppName() {
        return "analyzer";
    }

    @Override
    protected AbstractAppService getAppService() {
        return this.analyzerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnalyzerApp.class, args);
        LOG.info("APPLICATION FINISHED");
    }
}
