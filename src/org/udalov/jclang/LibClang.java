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

    int getDiagnosticSeverity(@NotNull Diagnostic diagnostic);

    @NotNull
    CXString formatDiagnostic(@NotNull Diagnostic diagnostic, int options);
}
