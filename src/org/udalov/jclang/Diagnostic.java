package org.udalov.jclang;

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;
import org.udalov.jclang.structs.CXString;

public class Diagnostic extends PointerType {
    public enum DisplayOptions {
        DISPLAY_SOURCE_LOCATION,
        DISPLAY_COLUMN,
        DISPLAY_SOURCE_RANGES,
        DISPLAY_OPTION,
        DISPLAY_CATEGORY_ID,
        DISPLAY_CATEGORY_NAME
    }

    @NotNull
    public String format(@NotNull DisplayOptions... options) {
        // TODO: dealloc
        int flags = 0;
        for (DisplayOptions option : options) {
            flags |= 1 << option.ordinal();
        }
        CXString string = LibClang.I.formatDiagnostic(this, flags);
        return LibClang.I.getCString(string);
    }
}
