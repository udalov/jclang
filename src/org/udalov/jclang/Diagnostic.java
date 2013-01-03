package org.udalov.jclang;

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;
import org.udalov.jclang.structs.CXString;

public class Diagnostic extends PointerType {
    // TODO: Set<Diagnostic.DisplayOptions> options
    @NotNull
    public String format(int options) {
        // TODO: dealloc
        CXString string = LibClang.I.formatDiagnostic(this, options);
        return LibClang.I.getCString(string);
    }
}
