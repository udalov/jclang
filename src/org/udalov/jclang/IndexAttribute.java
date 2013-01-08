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
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.structs.CXIdxAttrInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexAttribute {
    public enum Kind {
        UNEXPOSED,
        IB_ACTION,
        IB_OUTLET,
        IB_OUTLET_COLLECTION
    }

    private final Kind kind;
    private final Cursor cursor;
    private final IndexLocation location;

    public IndexAttribute(@NotNull CXIdxAttrInfo info) {
        this.kind = Kind.values()[info.kind];
        this.cursor = new Cursor(info.cursor);
        this.location = new IndexLocation(info.loc);
    }

    @NotNull
    /* package */ static List<IndexAttribute> createFromNative(@Nullable PointerByReference attributes, int numAttributes) {
        if (attributes == null || numAttributes == 0) {
            return Collections.emptyList();
        }
        CXIdxAttrInfo attrInfo = new CXIdxAttrInfo(attributes.getValue());
        List<IndexAttribute> result = new ArrayList<IndexAttribute>(numAttributes);
        for (int i = 0; i < numAttributes; i++) {
            // TODO: ugly hack, figure out how to make toArray() stuff work here
            CXIdxAttrInfo info = new CXIdxAttrInfo(attributes.getPointer().getPointer(i * attrInfo.size() / Pointer.SIZE));
            result.add(new IndexAttribute(info));
        }
        return result;
    }

    @NotNull
    public Kind getKind() {
        return kind;
    }

    @NotNull
    public Cursor getCursor() {
        return cursor;
    }

    @NotNull
    public IndexLocation getLocation() {
        return location;
    }
}
