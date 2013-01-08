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

    public IndexAttribute(@NotNull Kind kind, @NotNull Cursor cursor, @NotNull IndexLocation location) {
        this.kind = kind;
        this.cursor = cursor;
        this.location = location;
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
