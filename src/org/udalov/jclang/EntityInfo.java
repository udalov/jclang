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
import org.jetbrains.annotations.Nullable;
import org.udalov.jclang.structs.CXIdxEntityInfo;

import java.util.Collections;
import java.util.List;

// TODO: test everything unused
@SuppressWarnings("unused")
public class EntityInfo {
    public enum Kind {
        UNEXPOSED,
        TYPEDEF,
        FUNCTION,
        VARIABLE,
        FIELD,
        ENUM_CONSTANT,

        OBJC_CLASS,
        OBJC_PROTOCOL,
        OBJC_CATEGORY,

        OBJC_INSTANCE_METHOD,
        OBJC_CLASS_METHOD,
        OBJC_PROPERTY,
        OBJC_IVAR,

        ENUM,
        STRUCT,
        UNION,

        CXX_CLASS,
        CXX_NAMESPACE,
        CXX_NAMESPACE_ALIAS,
        CXX_STATIC_VARIABLE,
        CXX_STATIC_METHOD,
        CXX_INSTANCE_METHOD,
        CXX_CONSTRUCTOR,
        CXX_DESTRUCTOR,
        CXX_CONVERSION_FUNCTION,
        CXX_TYPE_ALIAS,
        CXX_INTERFACE
    }

    public enum CXXTemplateKind {
        NON_TEMPLATE,
        TEMPLATE,
        TEMPLATE_PARTIAL_SPECIALIZATION,
        TEMPLATE_SPECIALIZATION
    }

    public enum Language {
        NONE,
        C,
        OBJC,
        CXX
    }

    private final Kind kind;
    private final CXXTemplateKind cxxTemplateKind;
    private final Language language;
    private final String name;
    private final String usr;
    private final Cursor cursor;
    private final List<IndexAttribute> attributes;

    public EntityInfo(@NotNull CXIdxEntityInfo info) {
        this.kind = Kind.values()[info.kind];
        this.cxxTemplateKind = CXXTemplateKind.values()[info.templateKind];
        this.language = Language.values()[info.lang];
        this.name = info.name;
        this.usr = info.USR;
        this.cursor = new Cursor(info.cursor);
        this.attributes = IndexAttribute.createFromNative(info.attributes, info.numAttributes);
    }

    @NotNull
    public Kind getKind() {
        return kind;
    }

    @NotNull
    public CXXTemplateKind getCXXTemplateKind() {
        return cxxTemplateKind;
    }

    @NotNull
    public Language getLanguage() {
        return language;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NotNull
    public String getUSR() {
        return usr;
    }

    @NotNull
    public Cursor getCursor() {
        return cursor;
    }

    @NotNull
    public List<IndexAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }
}
