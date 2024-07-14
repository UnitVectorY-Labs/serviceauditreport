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
package com.unitvectory.serviceauditreport.serviceauditcore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The AnnotationUtils
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@UtilityClass
public class AnnotationUtils {

    /**
     * Retrieves the configuration class from the given package.
     * 
     * @param packageName the package to search for the configuration class
     * @return the configuration class
     */
    public static Class<?> getConfigurationClass(@NonNull String packageName) {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {

            ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(Config.class.getName());

            if (classInfoList.size() == 0) {
                throw new ServiceAuditReportException(
                        "No classes annotated with @Config found in package " + packageName);
            }

            if (classInfoList.size() > 1) {
                throw new ServiceAuditReportException(
                        "Multiple classes annotated with @Config found in package " + packageName);
            }

            ClassInfo classInfo = classInfoList.get(0);

            // TODO: Verify the @Config name parameter matches the packageName

            return classInfo.loadClass();
        }
    }

    /**
     * Retrieves all classes annotated with @Collector in the given package that
     * extend the AbstractService class.
     * 
     * @param packageName the package to search for classes
     * @param serviceType the class annotation to search for
     * @return a list of classes annotated with @Collector
     */
    public static List<AbstractService<?, ?>> getAnnotatedServices(@NonNull String packageName,
            @NonNull Class<?> serviceType) {
        List<AbstractService<?, ?>> instances = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {

            ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(serviceType.getName())
                    .filter(classInfo -> classInfo.extendsSuperclass(AbstractService.class.getName()));

            for (ClassInfo classInfo : classInfoList) {
                try {
                    Class<?> clazz = classInfo.loadClass();
                    if (AbstractService.class.isAssignableFrom(clazz)) {
                        AbstractService<?, ?> instance = (AbstractService<?, ?>) clazz.getDeclaredConstructor()
                                .newInstance();
                        instances.add(instance);
                    }
                } catch (Exception e) {
                    throw new ServiceAuditReportException("failed to create instance of class " + classInfo.getName(),
                            e);
                }
            }
        }

        return instances;
    }

    /**
     * Retrieves the value of the field annotated with @SetIdentifier from the given
     * object.
     *
     * @param instance the object from which to retrieve the value
     * @param <T>      the type of the object
     * @return the value of the field annotated with @SetIdentifier, or null if not
     *         found
     */
    public static <T> String getSetIdentifierValue(@NonNull T instance) {
        try {
            Class<?> clazz = instance.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(SetIdentifier.class)) {
                    // Make the field accessible
                    field.setAccessible(true);

                    // Make sure the field is an allowed type
                    if (String.class.equals(field.getType())) {
                        // String is allowed
                        return (String) field.get(instance);
                    } else {
                        throw new ServiceAuditReportException(
                                "Field " + field.getName()
                                        + " is not a string, but is annotated with @SetIdentifier; is type + "
                                        + field.getType().getName());
                    }
                }
            }

            throw new ServiceAuditReportException(
                    "No field annotated with @SetIdentifier found in class " + clazz.getName());
        } catch (Exception e) {
            throw new ServiceAuditReportException("failed to get identifier value", e);
        }
    }

    /**
     * Retrieves the DataEntity annotation for the given class.
     *
     * @param clazz the class to get the annotation from
     * @return the DataEntity annotation, or null if not present
     */
    public static DataEntity getDataEntityAnnotation(@NonNull Class<?> clazz) {
        if (clazz.isAnnotationPresent(DataEntity.class)) {
            return clazz.getAnnotation(DataEntity.class);
        }

        throw new ServiceAuditReportException("Class " + clazz.getName() + " does not have the DataEntity annotation");
    }
}
