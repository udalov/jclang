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

package org.udalov.jclang.structs;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.IndexerCallback;

@SuppressWarnings("unused")
public class NativeIndexerCallbacks extends Structure {
    // TODO: all callbacks
    public Callback abortQuery;
    public Callback diagnostic;
    public Callback enteredMainFile;
    public Callback ppIncludedFile;
    public Callback importedASTFile;
    public StartedTranslationUnitCallback startedTranslationUnit;
    public Callback indexDeclaration;
    public Callback indexEntityReference;

    public NativeIndexerCallbacks() {
        super();
        initFieldOrder();
    }

    public NativeIndexerCallbacks(@NotNull final IndexerCallback callback) {
        super();
        this.startedTranslationUnit = new StartedTranslationUnitCallback() {
            @Override
            public void apply(@Nullable Pointer clientData, @Nullable Pointer reserved) {
                callback.startedTranslationUnit();
            }
        };
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new String[]{"abortQuery", "diagnostic", "enteredMainFile", "ppIncludedFile", "importedASTFile",
                                   "startedTranslationUnit", "indexDeclaration", "indexEntityReference"});
    }

    public interface StartedTranslationUnitCallback extends Callback {
        void apply(@Nullable Pointer clientData, @Nullable Pointer reserved);
    }
}
