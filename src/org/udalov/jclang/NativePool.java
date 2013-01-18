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

import org.jetbrains.annotations.NotNull;
import org.udalov.jclang.structs.CXString;

import java.util.ArrayList;
import java.util.List;

/* package */ class NativePool {
    public static final NativePool I = new NativePool();

    private final List<CXString.ByValue> strings = new ArrayList<CXString.ByValue>();
    private final List<TranslationUnit> translationUnits = new ArrayList<TranslationUnit>();
    private final List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
    private final List<CXIndexAction> indexActions = new ArrayList<CXIndexAction>();
    private final List<Index> indexes = new ArrayList<Index>();

    private NativePool() {}

    public void record(@NotNull CXString.ByValue string) {
        strings.add(string);
    }

    @NotNull
    public TranslationUnit record(@NotNull TranslationUnit translationUnit) {
        translationUnits.add(translationUnit);
        return translationUnit;
    }

    @NotNull
    public Diagnostic record(@NotNull Diagnostic diagnostic) {
        diagnostics.add(diagnostic);
        return diagnostic;
    }

    @NotNull
    public CXIndexAction record(@NotNull CXIndexAction indexAction) {
        indexActions.add(indexAction);
        return indexAction;
    }

    @NotNull
    public Index record(@NotNull Index index) {
        indexes.add(index);
        return index;
    }

    public void disposeAll() {
        LibClang lib = LibClang.I;
        for (CXString.ByValue string : strings) {
            lib.disposeString(string);
        }
        for (TranslationUnit translationUnit : translationUnits) {
            lib.disposeTranslationUnit(translationUnit);
        }
        for (Diagnostic diagnostic : diagnostics) {
            lib.disposeDiagnostic(diagnostic);
        }
        for (CXIndexAction indexAction : indexActions) {
            lib.IndexAction_dispose(indexAction);
        }
        for (Index index : indexes) {
            lib.disposeIndex(index);
        }

        strings.clear();
        translationUnits.clear();
        diagnostics.clear();
        indexActions.clear();
        indexes.clear();
    }
}
