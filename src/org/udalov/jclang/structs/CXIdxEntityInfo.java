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

import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class CXIdxEntityInfo extends Structure {
    public int kind;
    public int templateKind;
    public int lang;
    public String name;
    public String USR;
    public CXCursor.ByValue cursor;
    @Nullable
    public PointerByReference /* CXIdxAttrInfo */ attributes;
    public int numAttributes;

    public CXIdxEntityInfo() {
        super();
        setFieldOrder(new String[]{"kind", "templateKind", "lang", "name", "USR", "cursor", "attributes", "numAttributes"});
    }

    public static class ByReference extends CXIdxEntityInfo implements Structure.ByReference {}
}
