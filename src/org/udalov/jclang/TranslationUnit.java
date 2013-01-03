package org.udalov.jclang;

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit extends PointerType {
    public enum Flag {
        DETAILED_PREPROCESSING_RECORD,
        INCOMPLETE,
        PRECOMPILED_PREAMBLE,
        CACHE_COMPLETION_RESULTS,
        FOR_SERIALIZATION,
        @Deprecated CXX_CHAINED_PCH,
        SKIP_FUNCTION_BODIES,
        INCLUDE_BRIEF_COMMENTS_IN_CODE_COMPLETION
    }

    @NotNull
    public List<Diagnostic> getDiagnostics() {
        int n = LibClang.I.getNumDiagnostics(this);
        List<Diagnostic> result = new ArrayList<Diagnostic>(n);
        for (int i = 0; i < n; i++) {
            // TODO: dealloc
            Diagnostic diagnostic = LibClang.I.getDiagnostic(this, i);
            result.add(diagnostic);
        }
        return result;
    }
}
