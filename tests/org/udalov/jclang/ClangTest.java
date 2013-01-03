package org.udalov.jclang;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class ClangTest extends TestCase {
    @NotNull
    protected Index createIndex(boolean excludeDeclarationsFromPCH, boolean displayDiagnostics) {
        return Clang.INSTANCE.createIndex(excludeDeclarationsFromPCH, displayDiagnostics);
    }

    @NotNull
    protected static File getTestDataDir() {
        return new File("testData/");
    }
}
