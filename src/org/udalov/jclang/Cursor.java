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
import org.udalov.jclang.structs.CXCursor;
import org.udalov.jclang.structs.CXString;

public class Cursor {
    private final CXCursor.ByValue cursor;

    public Cursor(@NotNull CXCursor.ByValue cursor) {
        this.cursor = cursor;
    }

    @NotNull
    public String getSpelling() {
        CXString.ByValue spelling = LibClang.I.getCursorSpelling(cursor);
        // TODO: dealloc
        String str = LibClang.I.getCString(spelling);
        return str;
    }

    @NotNull
    public CursorKind getKind() {
        // We should've called clang_getCursorKind here, but this works and is more efficient
        return CursorKind.fromNative(cursor.kind);
    }
}
