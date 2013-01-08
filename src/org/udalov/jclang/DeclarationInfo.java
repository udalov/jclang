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

import java.util.Collections;
import java.util.List;

public class DeclarationInfo {
    private final Cursor cursor;
    private final IndexLocation location;
    private final boolean isRedeclaration;
    private final boolean isDefinition;
    private final boolean isContainer;
    private final boolean isImplicit;
    private final List<IndexAttribute> attributes;

    public DeclarationInfo(
            @NotNull Cursor cursor,
            @NotNull IndexLocation location,
            boolean isRedeclaration,
            boolean isDefinition,
            boolean isContainer,
            boolean isImplicit,
            @NotNull List<IndexAttribute> attributes) {
        this.cursor = cursor;
        this.location = location;
        this.isRedeclaration = isRedeclaration;
        this.isDefinition = isDefinition;
        this.isContainer = isContainer;
        this.isImplicit = isImplicit;
        this.attributes = attributes;
    }

    @NotNull
    public Cursor getCursor() {
        return cursor;
    }

    @NotNull
    public IndexLocation getLocation() {
        return location;
    }

    public boolean isRedeclaration() {
        return isRedeclaration;
    }

    public boolean isDefinition() {
        return isDefinition;
    }

    public boolean isContainer() {
        return isContainer;
    }

    public boolean isImplicit() {
        return isImplicit;
    }

    @NotNull
    public List<IndexAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }
}
