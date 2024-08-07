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
package com.unitvectory.serviceauditreport.collector.app;

import org.junit.jupiter.api.Test;

/**
 * The CollectorAppTest
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class CollectorAppTest {

    @Test
    public void collectorAppTest() {
        // Test the main method which will error as missing parameters
        CollectorApp.main(new String[] {});
    }
}
