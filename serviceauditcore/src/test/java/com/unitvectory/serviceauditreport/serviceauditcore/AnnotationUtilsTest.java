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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * The AnnotationUtilsTest class.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class AnnotationUtilsTest {

    @Test
    public void getSetIdentifierValueTest() {
        DemoParentEntry parent = new DemoParentEntry();
        parent.setId("123");
        parent.setVal("abc");

        String id = AnnotationUtils.getSetIdentifierValue(parent);
        assertEquals("123", id);
    }

    @Test
    public void getSetIdentifierValueNullTest() {
        assertThrows(NullPointerException.class, () -> AnnotationUtils.getSetIdentifierValue(null));
    }

    @Test
    public void getSetIdentifierValueMissingTest() {
        DemoChildEntry child = new DemoChildEntry();
        child.setVal("abc");

        assertThrows(ServiceAuditReportException.class, () -> AnnotationUtils.getSetIdentifierValue(child));
    }

    @Test
    public void getDataEntityAnnotationTest() {
        DataEntity dataEntity = AnnotationUtils.getDataEntityAnnotation(DemoParentEntry.class);
        assertNotNull(dataEntity);
        assertEquals(AccessType.SET, dataEntity.accessType());
        assertEquals(Void.class, dataEntity.parent());
    }

    @Test
    public void getDataEntityAnnotationNullTest() {
        assertThrows(NullPointerException.class, () -> AnnotationUtils.getDataEntityAnnotation(null));
    }

    @Test
    public void getDataEntityAnnotationMissingTest() {
        assertThrows(ServiceAuditReportException.class, () -> AnnotationUtils.getDataEntityAnnotation(Object.class));
    }
}
