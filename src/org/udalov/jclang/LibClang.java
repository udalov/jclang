package org.udalov.jclang;

import com.sun.jna.FunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.structs.CXString;

import java.lang.reflect.Method;
import java.util.HashMap;

/* package */ interface LibClang extends Library {
    LibClang I = (LibClang) Native.loadLibrary("clang", LibClang.class, new HashMap<String, Object>() {{
        put(OPTION_FUNCTION_MAPPER, new FunctionMapper() {
            @Override
            public String getFunctionName(NativeLibrary library, Method method) {
                return "clang_" + method.getName();
            }
        });
    }});

    @NotNull
    String getCString(@NotNull CXString string);

    @NotNull
    Index createIndex(boolean excludeDeclarationsFromPCH, boolean displayDiagnostics);

    // TODO: class CXUnsavedFile extends Structure
    @Nullable
    TranslationUnit parseTranslationUnit(@NotNull Index index, @Nullable String sourceFilename, @Nullable String[] commandLineArgs,
                                         int numCommandLineArgs, @Nullable Void unsavedFiles, int numUnsavedFiles, int options);

    int getNumDiagnostics(@NotNull TranslationUnit unit);

    @NotNull
    Diagnostic getDiagnostic(@NotNull TranslationUnit unit, int index);

    @NotNull
    CXString formatDiagnostic(@NotNull Diagnostic diagnostic, int options);
}
