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

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.structs.*;

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
    String getCString(@NotNull CXString.ByValue string);

    void disposeString(@NotNull CXString.ByValue string);


    @NotNull
    CXString.ByValue getClangVersion();


    @NotNull
    CXString.ByValue getFileName(@NotNull CXFile file);


    @NotNull
    CXString.ByValue getCursorSpelling(@NotNull CXCursor.ByValue cursor);

    @NotNull
    CXString.ByValue getCursorKindSpelling(int kind);

    @NotNull
    CXType.ByValue getCursorType(@NotNull CXCursor.ByValue cursor);


    @NotNull
    CXString.ByValue getTypeKindSpelling(int kind);


    @NotNull
    Index createIndex(boolean excludeDeclarationsFromPCH, boolean displayDiagnostics);

    void disposeIndex(@NotNull Index index);

    // TODO: class CXUnsavedFile extends Structure
    @Nullable
    TranslationUnit parseTranslationUnit(@NotNull Index index, @Nullable String sourceFilename, @Nullable String[] commandLineArgs,
                                         int numCommandLineArgs, @Nullable Void unsavedFiles, int numUnsavedFiles, int options);

    void disposeTranslationUnit(@NotNull TranslationUnit translationUnit);


    @NotNull
    CXIndexAction IndexAction_create(@NotNull Index index);

    void IndexAction_dispose(@NotNull CXIndexAction action);

    int indexSourceFile(@NotNull CXIndexAction action, @Nullable Void clientData, @NotNull NativeIndexerCallbacks indexCallbacks,
                        int indexCallbacksSize, int indexOptions, @Nullable String sourceFilename, @Nullable String[] commandLineArgs,
                        int numCommandLineArgs, @Nullable Void unsavedFiles, int numUnsavedFiles,
                        @Nullable PointerByReference /* CXTranslationUnit */ translationUnit, int tuOptions);


    void indexLoc_getFileLocation(@NotNull CXIdxLoc.ByValue loc, @Nullable PointerByReference /* CXIdxClientFile */ indexFile,
                                  @Nullable PointerByReference /* CXFile */ file, @Nullable IntByReference line,
                                  @Nullable IntByReference column, @Nullable IntByReference offset);


    int getNumDiagnostics(@NotNull TranslationUnit unit);

    @NotNull
    Diagnostic getDiagnostic(@NotNull TranslationUnit unit, int index);

    void disposeDiagnostic(@NotNull Diagnostic diagnostic);

    int getDiagnosticSeverity(@NotNull Diagnostic diagnostic);

    @NotNull
    CXString.ByValue formatDiagnostic(@NotNull Diagnostic diagnostic, int options);
}
