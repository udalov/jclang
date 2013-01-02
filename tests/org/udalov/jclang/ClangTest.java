package org.udalov.jclang;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

public class ClangTest extends TestCase {
    @NotNull
    private Index createIndex(boolean excludeDeclarationsFromPCH, boolean displayDiagnostics) {
        return Clang.INSTANCE.createIndex(excludeDeclarationsFromPCH, displayDiagnostics);
    }


    public void testCreateIndex() {
        for (boolean excludeDeclarationsFromPCH : new boolean[]{false, true}) {
            for (boolean displayDiagnostics : new boolean[]{false, true}) {
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
}
