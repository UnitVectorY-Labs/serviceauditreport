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

import lombok.experimental.UtilityClass;

/**
 * The AnnotationUtils
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@UtilityClass
public class AnnotationUtils {

    /**
     * Retrieves the value of the field annotated with @SetIdentifier from the given
     * object.
     *
     * @param instance the object from which to retrieve the value
     * @param <T>      the type of the object
     * @return the value of the field annotated with @SetIdentifier, or null if not
     *         found
     */
    public static <T> String getSetIdentifierValue(T instance) {
        try {
            Class<?> clazz = instance.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(SetIdentifier.class)) {
                    // Make the field accessible
                    field.setAccessible(true);

                    // Make sure the field is a string
                    if (!String.class.equals(field.getType())) {
                        throw new ServiceAuditReportException(
                                "Field " + field.getName() + " is not a string, but is annotated with @SetIdentifier");
                    }

                    // Return the value of the field
                    return (String) field.get(instance);
                }
            }

            return null;
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
    public static DataEntity getDataEntityAnnotation(Class<?> clazz) {
        if (clazz.isAnnotationPresent(DataEntity.class)) {
            return clazz.getAnnotation(DataEntity.class);
        }

        throw new ServiceAuditReportException("Class " + clazz.getName() + " does not have the DataEntity annotation");
    }
}
