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

import org.pmw.tinylog.Logger;

import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerHierarchical;
import com.unitvectory.serviceauditreport.serviceauditcore.JacksonObjectMapper;
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

    private final File rootFolder;

    @Override
    public <T> List<T> load(@NonNull Class<T> clazz, ParentIdentifier parentIdentifier) {

        // TODO: Verify this logic, this will work for parents going up, but not for
        // other relatives
        // This load needs to be totally overhauled so that it can identify the
        // relationship so that it can load the correct data as it relates to the parent

        // After further thought I think I have the correct algorithm for evaluating this properly.
        if (parentIdentifier != null && clazz == parentIdentifier.getType()) {
            return load(clazz, parentIdentifier.getAncestor());
        }

        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(clazz);

        File folder = this.getFolder(rootFolder, parentIdentifier, true);

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

        File folder = this.getFolder(rootFolder, parentIdentifier, true);

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

    /**
     * Read a file
     * 
     * @param <T>   the class type
     * @param clazz the class
     * @param file  the file
     * @return the instance
     */
    private <T> T readFile(Class<T> clazz, File file) {
        try {
            Logger.info("Reading file: " + file.getAbsolutePath());
            return JacksonObjectMapper.OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw new ServiceAuditReportException("Failed to read instance from file", e);
        }
    }

    /**
     * Write a file
     * 
     * @param <T>      the class type
     * @param clazz    the class
     * @param instance the instance
     * @param file     the file
     */
    private <T> void writeFile(Class<T> clazz, T instance, File file) {
        try {
            Logger.info("Writing file: " + file.getAbsolutePath());
            JacksonObjectMapper.OBJECT_MAPPER.writeValue(file, instance);
        } catch (IOException e) {
            throw new ServiceAuditReportException("Failed to save instance to file", e);
        }
    }

    /**
     * Get the folder
     * 
     * @param folder           the folder
     * @param parentIdentifier the parent identifier
     * @param root             if this is the root
     * @return the folder
     */
    private File getFolder(File folder, ParentIdentifier parentIdentifier, boolean root) {

        // No parent, return the folder
        if (parentIdentifier == null) {
            return folder;
        }

        // Recurse to the root parent
        if (parentIdentifier.getAncestor() != null || parentIdentifier.getType() == Void.class) {
            folder = this.getFolder(folder, parentIdentifier.getAncestor(), false);
        }

        // Add the folder with the class name
        folder = new File(folder, parentIdentifier.getType().getName());

        // Add the folder with the id
        if (parentIdentifier.getId() != null) {
            folder = new File(folder, parentIdentifier.getId());
        }

        // If we are at the root, make the required directories
        if (root) {
            folder.mkdirs();
        }

        return folder;
    }
}
