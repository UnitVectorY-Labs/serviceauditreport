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
package com.unitvectory.serviceauditreport.core.app.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerHierarchical;
import com.unitvectory.serviceauditreport.serviceauditcore.AccessType;
import com.unitvectory.serviceauditreport.serviceauditcore.AnnotationUtils;
import com.unitvectory.serviceauditreport.serviceauditcore.DataEntity;
import com.unitvectory.serviceauditreport.serviceauditcore.ParentIdentifier;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceAuditReportException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * The FileDataManager
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor
public class FileDataManager implements DataManagerHierarchical {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final File rootFolder;

    @Override
    public <T> List<T> load(@NonNull Class<T> clazz, ParentIdentifier parentIdentifier) {
        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(clazz);

        File folder = this.getFolder(parentIdentifier);

        String name = clazz.getName();

        List<T> instances = new ArrayList<>();

        if (AccessType.SINGULAR.equals(dataEntity.accessType())) {

            // Singular file
            File file = new File(folder, name + ".json");

            // Read the file
            instances.add(this.readFile(clazz, file));

        } else if (AccessType.SET.equals(dataEntity.accessType())) {

            // Set folder
            File setFolder = new File(folder, name);
            setFolder.mkdirs();

            // Loop through all of the files in the folder
            for (File file : setFolder.listFiles()) {
                instances.add(this.readFile(clazz, file));
            }

        } else {
            throw new ServiceAuditReportException("Unknown access type: " + dataEntity.accessType());
        }

        return instances;
    }

    @Override
    public <T> void save(@NonNull Class<T> clazz, @NonNull T instance, ParentIdentifier parentIdentifier) {
        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(clazz);

        File folder = this.getFolder(parentIdentifier);

        String name = clazz.getName();

        if (AccessType.SINGULAR.equals(dataEntity.accessType())) {

            // Singular file
            File file = new File(folder, name + ".json");

            // Save the file
            this.writeFile(clazz, instance, file);

        } else if (AccessType.SET.equals(dataEntity.accessType())) {

            // Set folder
            File setFolder = new File(folder, name);
            setFolder.mkdirs();

            String id = AnnotationUtils.getSetIdentifierValue(instance);

            File file = new File(setFolder, id + ".json");

            // Save the file
            this.writeFile(clazz, instance, file);

        } else {
            throw new ServiceAuditReportException("Unknown access type: " + dataEntity.accessType());
        }
    }

    @Override
    public <T> void save(@NonNull Class<T> clazz, @NonNull List<T> instances, ParentIdentifier parentIdentifier) {
        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(clazz);

        File folder = this.getFolder(parentIdentifier);

        String name = clazz.getName();

        if (AccessType.SINGULAR.equals(dataEntity.accessType())) {

            // Singular file
            File file = new File(folder, name + ".json");

            // Save the file
            for (T instance : instances) {
                this.writeFile(clazz, instance, file);
            }

        } else if (AccessType.SET.equals(dataEntity.accessType())) {

            // Set folder
            File setFolder = new File(folder, name);
            setFolder.mkdirs();

            for (T instance : instances) {
                String id = AnnotationUtils.getSetIdentifierValue(instance);

                File file = new File(setFolder, id + ".json");

                // Save the file
                this.writeFile(clazz, instance, file);
            }
        } else {
            throw new ServiceAuditReportException("Unknown access type: " + dataEntity.accessType());
        }
    }

    private <T> T readFile(Class<T> clazz, File file) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new ServiceAuditReportException("Failed to read instance from file", e);
        }
    }

    private <T> void writeFile(Class<T> clazz, T instance, File file) {
        try {
            objectMapper.writeValue(file, instance);
        } catch (IOException e) {
            throw new ServiceAuditReportException("Failed to save instance to file", e);
        }
    }

    private File getFolder(ParentIdentifier parentIdentifier) {
        File file = rootFolder;

        // Iterate throught the parents to get the directory
        ParentIdentifier parent = parentIdentifier;
        while (parent != null) {
            file = new File(file, parent.getId());
            parent = parent.getAncestor();
        }

        // Make the folder
        file.mkdirs();

        return file;
    }
}
