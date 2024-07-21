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

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

/**
 * The Demo App Test
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class DemoAppTest {

    @Test
    public void demoAppNoArgsTest() {
        // Test the main method which will error as missing parameters
        DemoApp.main(new String[] {});
    }

    @Test
    public void demoAppTestArgs(@TempDir(cleanup = CleanupMode.ON_SUCCESS) Path tempDir) {
        // Convert tempDir to absolute path string
        String tempPath = tempDir.toAbsolutePath().toString();

        // Build the absolute path to the file test-configs.json in the test resources
        String inputPath = getClass().getClassLoader().getResource("test-config.json").getPath();

        // Test the main method with arguments
        DemoApp.main(new String[] { "-i", inputPath, "-o", tempPath });
    }
}
