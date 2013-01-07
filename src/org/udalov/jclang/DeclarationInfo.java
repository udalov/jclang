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

public class DeclarationInfo {
    private final Cursor cursor;
    private final boolean isRedeclaration;
    private final boolean isDefinition;
    private final boolean isContainer;
    private final boolean isImplicit;

    public DeclarationInfo(@NotNull Cursor cursor, boolean isRedeclaration, boolean isDefinition, boolean isContainer, boolean isImplicit) {
        this.cursor = cursor;
        this.isRedeclaration = isRedeclaration;
        this.isDefinition = isDefinition;
        this.isContainer = isContainer;
        this.isImplicit = isImplicit;
    }

    @NotNull
    public Cursor getCursor() {
        return cursor;
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
}
