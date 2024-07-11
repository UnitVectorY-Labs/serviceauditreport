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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * The Parent Identifier Test class.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class ParentIdentifierTest {

    @Test
    public void ofSetTest() {
        DemoParentEntry parent = new DemoParentEntry();
        parent.setId("123");
        parent.setVal("abc");

        ParentIdentifier parentIdentifier = ParentIdentifier.of(parent);
        assertNotNull(parentIdentifier);
        assertEquals("123", parentIdentifier.getId());
        assertEquals(DemoParentEntry.class, parentIdentifier.getType());
        assertNull(parentIdentifier.getAncestor());
    }

    @Test
    public void ofSingularTest(){
        DemoChildEntry child = new DemoChildEntry();
        child.setVal("def");

        ParentIdentifier parentIdentifier = ParentIdentifier.of(child);
        assertNotNull(parentIdentifier);
        assertNull(parentIdentifier.getId());
        assertEquals(DemoChildEntry.class, parentIdentifier.getType());
        assertNull(parentIdentifier.getAncestor());
    }
}
