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
package com.unitvectory.serviceauditreport.reporter.app;

import org.pmw.tinylog.Logger;

import com.unitvectory.serviceauditreport.core.app.AbstractApp;

import lombok.AllArgsConstructor;

/**
 * The Reporter App
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor
public class ReporterApp extends AbstractApp {

    @Override
    protected String getAppName() {
        return "reporter";
    }

    /**
     * The main method
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
        Logger.info("APPLICATION STARTED");
        new ReporterApp().run(args);
        Logger.info("APPLICATION FINISHED");
    }
}
