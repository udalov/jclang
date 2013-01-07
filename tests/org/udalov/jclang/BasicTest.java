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

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.udalov.jclang.Diagnostic.DisplayOptions.*;
import static org.udalov.jclang.Diagnostic.Severity;
import static org.udalov.jclang.Diagnostic.Severity.*;
import static org.udalov.jclang.TestUtils.createOrCompare;

public class BasicTest extends ClangTest {
    @NotNull
    private String getDir() {
        return getTestDataDir() + "/basic/";
    }

    public void testCreateIndex() {
        boolean[] booleans = {false, true};
        for (boolean excludeDeclarationsFromPCH : booleans) {
            for (boolean displayDiagnostics : booleans) {
                assertNotNull(Clang.INSTANCE.createIndex(excludeDeclarationsFromPCH, displayDiagnostics));
            }
        }
    }

    public void testParseTranslationUnit() {
        Index index = Clang.INSTANCE.createIndex(false, false);
        index.parseTranslationUnit(TestUtils.createTempFileWithContents("").getAbsolutePath(), new String[]{});
    }

    public void testTranslationException() {
        Index index = Clang.INSTANCE.createIndex(false, false);
        try {
            index.parseTranslationUnit(null, new String[]{});
        } catch (TranslationException e) {
            return;
        }
        fail("TranslationException expected");
    }

    public void testDiagnosticDisplayOptions() {
        Index index = Clang.INSTANCE.createIndex(false, false);
        TranslationUnit unit = index.parseTranslationUnit(getDir() + "diagnosticDisplayOptions.h", new String[]{});
        List<Diagnostic> diagnostics = unit.getDiagnostics();
        assertEquals(1, diagnostics.size());
        Diagnostic diagnostic = diagnostics.iterator().next();
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        out.println(diagnostic.format(DISPLAY_SOURCE_LOCATION));
        out.println(diagnostic.format(DISPLAY_SOURCE_LOCATION, DISPLAY_COLUMN));
        out.println(diagnostic.format(DISPLAY_SOURCE_LOCATION, DISPLAY_SOURCE_RANGES));
        // TODO: option is not available for this particular warning, find another one where it is
        out.println(diagnostic.format(DISPLAY_OPTION));
        out.println(diagnostic.format(DISPLAY_CATEGORY_ID));
        out.println(diagnostic.format(DISPLAY_CATEGORY_NAME));
        out.close();
        createOrCompare(sw.toString(), getDir() + "diagnosticDisplayOptions.txt");
    }

    public void testDiagnosticSeverity() {
        Index index = Clang.INSTANCE.createIndex(false, false);
        TranslationUnit unit = index.parseTranslationUnit(getDir() + "diagnosticSeverity.h", new String[]{});
        List<Diagnostic> diagnostics = unit.getDiagnostics();
        List<Severity> actual = new ArrayList<Severity>(diagnostics.size());
        for (Diagnostic diagnostic : diagnostics) {
            actual.add(diagnostic.getSeverity());
        }
        assertEquals(Arrays.asList(ERROR, WARNING, FATAL), actual);
    }
}
