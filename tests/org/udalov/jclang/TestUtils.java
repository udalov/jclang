/*
 * Copyright 2013 Alexander Udalov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.udalov.jclang;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class TestUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private TestUtils() {}

    @NotNull
    public static String loadFileContents(@NotNull String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s).append(LINE_SEPARATOR);
        }
        reader.close();
        return sb.toString();
    }

    @NotNull
    public static File createFileWithContents(@NotNull String contents) {
        try {
            File dummy = File.createTempFile("jclang", ".h");
            PrintWriter writer = new PrintWriter(dummy);
            writer.println(contents);
            writer.close();
            return dummy;
        }
        catch (Exception e) {
            throw rethrow(e);
        }
    }

    @NotNull
    public static RuntimeException rethrow(@NotNull Exception e) {
        throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }

    public static void createOrCompare(@NotNull String actual, @NotNull String expectedFileName) {
        try {
            String expected = loadFileContents(expectedFileName);
            Assert.assertEquals(expected, actual);
        }
        catch (IOException e) {
            try {
                PrintWriter out = new PrintWriter(new File(expectedFileName));
                out.println(actual);
                out.close();
                Assert.fail("Expected file wasn't found, it will be created");
            }
            catch (IOException ee) {
                ee.printStackTrace();
                Assert.fail(ee.getMessage());
            }
        }
    }
}
