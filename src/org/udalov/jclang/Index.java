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

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.structs.NativeIndexerCallbacks;
import org.udalov.jclang.util.Util;

import static org.udalov.jclang.TranslationUnit.Flag;

public class Index extends PointerType {
    @NotNull
    public TranslationUnit parseTranslationUnit(@Nullable String sourceFilename, @NotNull String[] args, @NotNull Flag... options)
            throws TranslationException {
        int flags = Util.buildOptionsMask(options);
        // TODO: dealloc
        TranslationUnit translationUnit = LibClang.I.parseTranslationUnit(this, sourceFilename, args, args.length, null, 0, flags);
        if (translationUnit == null) {
            throw new TranslationException();
        }
        return translationUnit;
    }

    @NotNull
    private Pointer createIndexAction() {
        // TODO: dealloc after the translation unit
        return LibClang.I.IndexAction_create(this);
    }

    @NotNull
    public TranslationUnit indexSourceFile(@NotNull IndexerCallback callback, @Nullable String sourceFilename, @NotNull String[] args, @NotNull Flag... options) throws IndexException {
        Pointer indexAction = createIndexAction();
        NativeIndexerCallbacks callbacks = new NativeIndexerCallbacks(callback);
        int flags = Util.buildOptionsMask(options);
        PointerByReference tuRef = new PointerByReference();
        int exitCode = LibClang.I.indexSourceFile(indexAction, null, callbacks, callbacks.size(), 0 /* TODO: CXIndexOptFlags */,
                sourceFilename, args, args.length, null, 0, tuRef, flags);
        if (exitCode != 0) {
            throw new IndexException(exitCode);
        }
        TranslationUnit tu = new TranslationUnit();
        tu.setPointer(tuRef.getValue());
        return tu;
    }
}
