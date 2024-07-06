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
package com.unitvectory.serviceauditreport.core.app.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitvectory.serviceauditreport.core.persistence.AbstractPersistenceService;

import lombok.AllArgsConstructor;

/**
 * The FilePersistenceService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor
public class FilePersistenceService extends AbstractPersistenceService {

    private ObjectMapper objectMapper;

    private File filePersistenceRoot;

    @Override
    public void saveData(Object data, String file, String... path) {
        // Use Jackson to save the data to a file
        try {
            // Convert the object to JSON string
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);

            // Use the filePersistenceRoot to create the full path
            File fullPath = new File(filePersistenceRoot, String.join("/", path));
            if (!fullPath.exists()) {
                fullPath.mkdirs(); // Create the path if it does not exist
            }

            // Write the JSON string to the file
            File f = new File(fullPath, file);

            System.out.println("Saving data to " + f.getAbsolutePath());

            FileUtils.writeStringToFile(f, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Handle the exception
            throw new RuntimeException("Failed to save data", e);
        }
    }

    @Override
    public <T> T loadData(Class<T> clazz, String file, String... path) {
        // Use Jackson to load the data from a file
        try {
            // Use the filePersistenceRoot to create the full path
            File fullPath = new File(filePersistenceRoot, String.join("/", path));

            // Read the JSON string from the file
            File f = new File(fullPath, file);
            return objectMapper.readValue(f, clazz);
        } catch (IOException e) {
            // Handle the exception
            throw new RuntimeException("Failed to load data", e);
        }
    }
}