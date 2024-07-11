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
package com.unitvectory.serviceauditreport.core.app;

import org.pmw.tinylog.Logger;

/**
 * The Demo App
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class DemoApp extends AbstractApp {

    @Override
    protected String getAppName() {
        return "demo";
    }

    /**
     * The Demo App main method
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
        Logger.info("APPLICATION STARTED");
        new DemoApp().run(args);
        Logger.info("APPLICATION FINISHED");
    }
}