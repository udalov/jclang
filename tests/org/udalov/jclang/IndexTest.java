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

public class IndexTest extends ClangTest {
    @NotNull
    private String getDir() {
        return getTestDataDir() + "/index/";
    }

    @NotNull
    private TranslationUnit indexTestDeclarations(@NotNull IndexerCallback callback) {
        Index index = Clang.INSTANCE.createIndex(false, false);
        return index.indexSourceFile(callback, getDir() + "declarations.h", new String[]{});
    }

    public void testIndexSourceFileWithEmptyCallback() {
        TranslationUnit unit = indexTestDeclarations(IndexerCallback.DO_NOTHING);
        assertTrue(unit.getDiagnostics().isEmpty());
    }

    public void testStartedTranslationUnit() {
        final boolean[] started = new boolean[] {false};
        indexTestDeclarations(new IndexerCallback() {
            @Override
            public void startedTranslationUnit() {
                assertFalse(started[0]);
                started[0] = true;
            }
        });
        assertTrue(started[0]);
    }
}
