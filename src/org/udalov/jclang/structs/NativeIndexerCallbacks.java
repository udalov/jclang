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
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class NativeIndexerCallbacks extends Structure {
    // TODO: all callbacks
    public Callback abortQuery;
    public Callback diagnostic;
    public EnteredMainFileCallback enteredMainFile;
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
        this.enteredMainFile = new EnteredMainFileCallback() {
            @Nullable
            @Override
            public Pointer apply(@Nullable Pointer clientData, @NotNull CXFile mainFile, @Nullable Pointer reserved) {
                callback.enteredMainFile(mainFile.toFile());
                return null;
            }
        };
        this.startedTranslationUnit = new StartedTranslationUnitCallback() {
            @Override
            public void apply(@Nullable Pointer clientData, @Nullable Pointer reserved) {
                callback.startedTranslationUnit();
            }
        };
        this.indexDeclaration = new IndexDeclarationCallback() {
            @Override
            public void apply(@Nullable Pointer clientData, @NotNull CXIdxDeclInfo.ByReference info) {
                DeclarationInfo declarationInfo = new DeclarationInfo(
                        new Cursor(info.cursor),
                        new IndexLocation(info.loc),
                        info.isRedeclaration,
                        info.isDefinition,
                        info.isContainer,
                        info.isImplicit,
                        getIndexAttributes(info.attributes, info.numAttributes)
                );
                callback.indexDeclaration(declarationInfo);
            }

            @NotNull
            private List<IndexAttribute> getIndexAttributes(@Nullable PointerByReference attributes, int numAttributes) {
                if (attributes == null || numAttributes == 0) {
                    return Collections.emptyList();
                }
                CXIdxAttrInfo attrInfo = new CXIdxAttrInfo(attributes.getValue());
                List<IndexAttribute> result = new ArrayList<IndexAttribute>(numAttributes);
                for (int i = 0; i < numAttributes; i++) {
                    // TODO: ugly hack, figure out how to make toArray() stuff work here
                    CXIdxAttrInfo info = new CXIdxAttrInfo(attributes.getPointer().getPointer(i * attrInfo.size() / Pointer.SIZE));
                    IndexAttribute attribute = new IndexAttribute(
                            IndexAttribute.Kind.values()[info.kind],
                            new Cursor(info.cursor),
                            new IndexLocation(info.loc)
                    );
                    result.add(attribute);
                }
                return result;
            }
        };
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new String[]{"abortQuery", "diagnostic", "enteredMainFile", "ppIncludedFile", "importedASTFile",
                                   "startedTranslationUnit", "indexDeclaration", "indexEntityReference"});
    }

    public interface EnteredMainFileCallback extends Callback {
        @Nullable
        Pointer apply(@Nullable Pointer clientData, @NotNull CXFile mainFile, @Nullable Pointer reserved);
    }

    public interface StartedTranslationUnitCallback extends Callback {
        void apply(@Nullable Pointer clientData, @Nullable Pointer reserved);
    }

    public interface IndexDeclarationCallback extends Callback {
        void apply(@Nullable Pointer clientData, @NotNull CXIdxDeclInfo.ByReference info);
    }
}
