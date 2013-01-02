package org.udalov.jclang;

import org.jetbrains.annotations.NotNull;

public class Clang {
    public static final Clang INSTANCE = new Clang();

    private Clang() {}

    @NotNull
    public Index createIndex(boolean excludeDeclarationsFromPCH, boolean displayDiagnostics) {
        // TODO: dealloc
        Index index = LibClang.I.createIndex(excludeDeclarationsFromPCH, displayDiagnostics);
        return index;
    }
}
