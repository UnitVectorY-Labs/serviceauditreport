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

import lombok.NonNull;
import lombok.Value;

/**
 * The parent identifier class.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Value
public class ParentIdentifier {

    /**
     * The identifier.
     */
    private final String id;

    /**
     * The type.
     */
    private final Class<?> type;

    /**
     * The ancestor.
     * 
     * Allows for a parent to have a parent for an arbitrary number of levels.
     */
    private final ParentIdentifier ancestor;

    /**
     * Create a new parent identifier.
     * 
     * This must be constructed from the root
     * 
     * @param object   the object
     * @param ancestor the ancestor
     * @return the parent identifier
     */
    public static ParentIdentifier of(@NonNull Object object, ParentIdentifier ancestor) {
        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(object.getClass());
        if (AccessType.SINGULAR.equals(dataEntity.accessType())) {
            // Singular
            return new ParentIdentifier(null, object.getClass(), ancestor);
        } else if (AccessType.SET.equals(dataEntity.accessType())) {
            // Set
            String id = AnnotationUtils.getSetIdentifierValue(object);
            return new ParentIdentifier(id, object.getClass(), ancestor);
        } else {
            throw new ServiceAuditReportException("Unknown access type: " + dataEntity.accessType());
        }
    }

    /**
     * Create a new parent identifier.
     * 
     * This has no ancestors.
     * 
     * @param object the object
     * @return the parent identifier
     */
    public static ParentIdentifier of(@NonNull Object object) {
        return of(object, null);
    }
}
