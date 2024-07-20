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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitvectory.jsonassertify.JSONAssert;
import com.unitvectory.serviceauditreport.serviceauditcore.ParentIdentifier;

/**
 * The FileDataManagerTest
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class FileDataManagerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Singular

    @Test
    public void demoSingularTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the object
        DemoSingularEntry write = new DemoSingularEntry("a", "b");
        fileDataManager.save(DemoSingularEntry.class, write);

        // Read the object
        List<DemoSingularEntry> read = fileDataManager.load(DemoSingularEntry.class);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSingularEntry.json");
    }

    // Set

    @Test
    public void demoSetTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the object
        DemoSetEntry write = new DemoSetEntry("1", "a", "b");
        fileDataManager.save(DemoSetEntry.class, write);

        // Read the object
        List<DemoSetEntry> read = fileDataManager.load(DemoSetEntry.class);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSetEntry", "1.json");
    }

    // Set -> Set

    @Test
    public void demoSetSetTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the first set
        List<DemoSetEntry> set = List.of(new DemoSetEntry("1", "a", "b"));
        fileDataManager.saveList(DemoSetEntry.class, set);
        ParentIdentifier parent = ParentIdentifier.of(set.get(0));

        // Write the object
        DemoSetSetEntry write = new DemoSetSetEntry("s", "v");
        fileDataManager.save(DemoSetSetEntry.class, write, parent);

        // Read the object
        List<DemoSetSetEntry> read = fileDataManager.load(DemoSetSetEntry.class, parent);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSetEntry", "1",
                "com.unitvectory.serviceauditreport.core.app.data.DemoSetSetEntry", "s.json");
    }

    // Set -> Singular

    @Test
    public void demoSetSingularTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the first set
        List<DemoSetEntry> set = List.of(new DemoSetEntry("1", "a", "b"));
        fileDataManager.saveList(DemoSetEntry.class, set);
        ParentIdentifier parent = ParentIdentifier.of(set.get(0));

        // Write the object
        DemoSetSingularEntry write = new DemoSetSingularEntry( "p");
        fileDataManager.save(DemoSetSingularEntry.class, write, parent);

        // Read the object
        List<DemoSetSingularEntry> read = fileDataManager.load(DemoSetSingularEntry.class, parent);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSetEntry", "1",
                "com.unitvectory.serviceauditreport.core.app.data.DemoSetSingularEntry.json");
    }

    // Singular -> Set

    @Test
    public void demoSingularSetTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the first set
        List<DemoSingularEntry> set = List.of(new DemoSingularEntry("x", "y"));
        fileDataManager.saveList(DemoSingularEntry.class, set);
        ParentIdentifier parent = ParentIdentifier.of(set.get(0));

        // Write the object
        DemoSingularSetEntry write = new DemoSingularSetEntry("q", "f");
        fileDataManager.save(DemoSingularSetEntry.class, write, parent);

        // Read the object
        List<DemoSingularSetEntry> read = fileDataManager.load(DemoSingularSetEntry.class, parent);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSingularEntry",
                "com.unitvectory.serviceauditreport.core.app.data.DemoSingularSetEntry", "q.json");
    }

    // Singular -> Singular

    @Test
    public void demoSingularSingularTest(@TempDir File tempDir) throws Exception {

        FileDataManager fileDataManager = new FileDataManager(tempDir);

        // Write the first set
        List<DemoSingularEntry> set = List.of(new DemoSingularEntry("x", "y"));
        fileDataManager.saveList(DemoSingularEntry.class, set);
        ParentIdentifier parent = ParentIdentifier.of(set.get(0));

        // Write the object
        DemoSingularSingularEntry write = new DemoSingularSingularEntry( "z");
        fileDataManager.save(DemoSingularSingularEntry.class, write, parent);

        // Read the object
        List<DemoSingularSingularEntry> read = fileDataManager.load(DemoSingularSingularEntry.class, parent);

        // Assert they are equal
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals(write, read.get(0));

        // Manually read the file and assert that they are equal
        verifyFile(write, tempDir, "com.unitvectory.serviceauditreport.core.app.data.DemoSingularEntry",
                "com.unitvectory.serviceauditreport.core.app.data.DemoSingularSingularEntry.json");
    }
    
    /**
     * Helper method to verify the file contents are equal to the object
     * 
     * @param object    The object to verify
     * @param directory The directory to verify the file in
     * @param path      The path to the file
     * @throws Exception If there is an error
     */
    private void verifyFile(Object object, File directory, String... path) throws Exception {
        // Serialize the object into JSON
        String expected = OBJECT_MAPPER.writeValueAsString(object);

        // Load the file from the path directory under the path
        File file = new File(directory + File.separator + String.join(File.separator, path));
        assertTrue(file.exists());
        String actual = new String(Files.readAllBytes(file.toPath()));

        // Use the JSONAssert.assertEquals to compare the two
        assertTrue(file.exists());
        JSONAssert.assertEquals(expected, actual, true);
    }
}
