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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Class<?>, DataEntity> getClassTypeMap(Class<?> clazz) {
        Map<Class<?>, DataEntity> types = new LinkedHashMap<>();
        do {
            DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(clazz);
            types.put(clazz, dataEntity);

            clazz = dataEntity.parent();
        } while (clazz != Void.class);
        return types;
    }

    private Map<Class<?>, ParentIdentifier> getParentIdentifierMap(ParentIdentifier parentIdentifier) {
        Map<Class<?>, ParentIdentifier> types = new LinkedHashMap<>();
        while (parentIdentifier != null) {
            types.put(parentIdentifier.getType(), parentIdentifier);
            parentIdentifier = parentIdentifier.getAncestor();
        }
        return types;
    }

    public <T> List<T> load(@NonNull Class<T> loadClass, ParentIdentifier parentIdentifier) {

        // The first step is to deteremine the tree structure that is relevant to this
        Map<Class<?>, DataEntity> dataEntityMap = this.getClassTypeMap(loadClass);

        // The second step is to determine the parent identifiers that are relevant to
        // this
        Map<Class<?>, ParentIdentifier> parentIdentifierMap = this.getParentIdentifierMap(parentIdentifier);

        // Get the list of classes in the order from root to leafe that we are going to
        // walk though
        List<Class<?>> classes = new ArrayList<>(dataEntityMap.keySet());
        Collections.reverse(classes);

        // Start at the root, we are going to
        List<File> folders = new ArrayList<>();
        folders.add(rootFolder);

        List<T> instances = new ArrayList<>();

        // Walk through the tree
        for (Class<?> traverseClass : classes) {

            // Special case for the load class
            if (loadClass == traverseClass) {
                // We've reached the end so we can finally load the data

                for (File folder : folders) {

                    DataEntity dataEntity = dataEntityMap.get(traverseClass);
                    String name = traverseClass.getName();

                    if (AccessType.SINGULAR.equals(dataEntity.accessType())) {

                        // Singular file
                        File file = new File(folder, name + ".json");

                        // Read the file
                        instances.add(this.readFile(loadClass, file));

                    } else if (AccessType.SET.equals(dataEntity.accessType())) {

                        // Set folder
                        File setFolder = new File(folder, name);
                        setFolder.mkdirs();

                        // Loop through all of the files in the folder
                        for (File file : setFolder.listFiles()) {
                            // We only want files
                            if (!file.isFile()) {
                                continue;
                            }

                            // Skip the non-JSON files
                            if (!file.getName().endsWith(".json")) {
                                continue;
                            }
                            
                            instances.add(this.readFile(loadClass, file));
                        }

                    } else {
                        throw new ServiceAuditReportException("Unknown access type: " + dataEntity.accessType());
                    }

                }

            } else {
                // We are still traversing the tree so we need to identify the files

                List<File> newFolders = new ArrayList<>();

                // Loop through all of the folders
                for (File folder : folders) {
                    DataEntity folderDataEntity = dataEntityMap.get(traverseClass);
                    ParentIdentifier folderParentIdentifier = parentIdentifierMap.get(traverseClass);

                    newFolders.addAll(identifyFolders(folder, traverseClass, folderDataEntity, folderParentIdentifier));
                }

                // Move onto the next level for the next iteration
                folders = newFolders;
            }
        }

        return instances;
    }

    public List<File> identifyFolders(File folder, Class<?> traverseClass, DataEntity folderDataEntity,
            ParentIdentifier folderParentIdentifier) {
        List<File> files = new ArrayList<>();

        if (AccessType.SINGULAR.equals(folderDataEntity.accessType())) {

            // Singular file
            File singularFolder = new File(folder, traverseClass.getName());
            if (singularFolder.exists() && singularFolder.isDirectory()) {
                files.add(singularFolder);
            }

            return files;

        } else if (AccessType.SET.equals(folderDataEntity.accessType())) {

            // Set folder

            File setFolder = new File(folder, traverseClass.getName());
            if (!setFolder.exists() || !setFolder.isDirectory()) {
                // File does not exist
                return files;
            }

            if (folderParentIdentifier != null) {
                // This is limited to a single folder
                File idFolder = new File(setFolder, folderParentIdentifier.getId());
                if (idFolder.exists() && idFolder.isDirectory()) {
                    files.add(idFolder);
                }

            } else {

                // Loop through all of the files in the folder
                for (File file : setFolder.listFiles()) {
                    // Skip the non-folders
                    if (!file.isDirectory()) {
                        continue;
                    }

                    // Performing a double check, if there isn't a corresponding JSON file in the
                    // folder then the folder is not one of the ones we are expecting and could
                    // cause errors
                    File jsonFile = new File(file, file.getName() + ".json");
                    if (!jsonFile.exists()) {
                        continue;
                    }

                    files.add(file);
                }
            }

            return files;

        } else {
            throw new ServiceAuditReportException("Unknown access type: " + folderDataEntity.accessType());
        }
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
