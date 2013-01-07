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

import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.udalov.jclang.Diagnostic.DisplayOptions.DISPLAY_SOURCE_LOCATION;
import static org.udalov.jclang.TestUtils.createOrCompare;

public class DiagnosticTest extends ClangTest {
    private final File file;
    private static final String EXPECTED_SUFFIX = ".expected";

    private DiagnosticTest(@NotNull File file) {
        this.file = file;
    }

    @Override
    protected void runTest() throws Throwable {
        Index index = Clang.INSTANCE.createIndex(false, false);
        TranslationUnit unit = index.parseTranslationUnit(file.getPath(), new String[]{});
        List<Diagnostic> diagnostics = unit.getDiagnostics();
        String actual = serializeDiagnostics(diagnostics);
        createOrCompare(actual, file + EXPECTED_SUFFIX);
    }

    @Override
    @NotNull
    public String getName() {
        return file.getName();
    }

    @NotNull
    private String serializeDiagnostics(@NotNull List<Diagnostic> diagnostics) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        for (Diagnostic diagnostic : diagnostics) {
            String s = diagnostic.format(DISPLAY_SOURCE_LOCATION);
            out.println(s.substring(s.lastIndexOf('/') + 1));
        }
        out.close();
        return sw.toString();
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        File dir = new File(getTestDataDir(), "diagnostic");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().endsWith(EXPECTED_SUFFIX)) {
                    suite.addTest(new DiagnosticTest(file));
                }
            }
        }
        return suite;
    }
}
