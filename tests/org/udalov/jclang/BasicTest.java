package org.udalov.jclang;

public class BasicTest extends ClangTest {
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
}
