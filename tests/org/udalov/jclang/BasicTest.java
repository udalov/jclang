package org.udalov.jclang;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.udalov.jclang.Diagnostic.DisplayOptions.*;

public class BasicTest extends ClangTest {
    @NotNull
    private String getDir() {
        return getTestDataDir() + "/basic/";
    }

    public void testCreateIndex() {
        boolean[] booleans = {false, true};
        for (boolean excludeDeclarationsFromPCH : booleans) {
            for (boolean displayDiagnostics : booleans) {
                assertNotNull(createIndex(excludeDeclarationsFromPCH, displayDiagnostics));
            }
        }
    }

    public void testParseTranslationUnit() {
        Index index = createIndex(false, false);
        try {
            index.parseTranslationUnit(null, 0);
        } catch (TranslationException e) {
            // OK
            return;
        }
        fail("TranslationException expected");
    }

    public void testDiagnosticDisplayOptions() {
        Index index = createIndex(false, false);
        TranslationUnit unit = index.parseTranslationUnit(getDir() + "diagnosticDisplayOptions.h", 0);
        List<Diagnostic> diagnostics = unit.getDiagnostics();
        assertEquals(1, diagnostics.size());
        Diagnostic diagnostic = diagnostics.iterator().next();
        StringWriter str = new StringWriter();
        PrintWriter writer = new PrintWriter(str);
        writer.println(diagnostic.format(DISPLAY_SOURCE_LOCATION));
        writer.println(diagnostic.format(DISPLAY_SOURCE_LOCATION, DISPLAY_COLUMN));
        writer.println(diagnostic.format(DISPLAY_SOURCE_LOCATION, DISPLAY_SOURCE_RANGES));
        // TODO: option is not available for this particular warning, find another one where it is
        writer.println(diagnostic.format(DISPLAY_OPTION));
        writer.println(diagnostic.format(DISPLAY_CATEGORY_ID));
        writer.println(diagnostic.format(DISPLAY_CATEGORY_NAME));
        writer.close();
        assertEquals(TestUtils.loadFileContents(getDir() + "diagnosticDisplayOptions.txt"), str.toString());
    }
}
