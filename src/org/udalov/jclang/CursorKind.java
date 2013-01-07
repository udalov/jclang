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
import org.udalov.jclang.structs.CXString;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum CursorKind {
    UNEXPOSED_DECL,
    STRUCT_DECL,
    UNION_DECL,
    CLASS_DECL,
    ENUM_DECL,
    FIELD_DECL,
    ENUM_CONSTANT_DECL,
    FUNCTION_DECL,
    VAR_DECL,
    PARM_DECL,
    OBJC_INTERFACE_DECL,
    OBJC_CATEGORY_DECL,
    OBJC_PROTOCOL_DECL,
    OBJC_PROPERTY_DECL,
    OBJC_IVAR_DECL,
    OBJC_INSTANCE_METHOD_DECL,
    OBJC_CLASS_METHOD_DECL,
    OBJC_IMPLEMENTATION_DECL,
    OBJC_CATEGORY_IMPL_DECL,
    TYPEDEF_DECL,
    CXX_METHOD,
    NAMESPACE,
    LINKAGE_SPEC,
    CONSTRUCTOR,
    DESTRUCTOR,
    CONVERSION_FUNCTION,
    TEMPLATE_TYPE_PARAMETER,
    NON_TYPE_TEMPLATE_PARAMETER,
    TEMPLATE_TEMPLATE_PARAMETER,
    FUNCTION_TEMPLATE,
    CLASS_TEMPLATE,
    CLASS_TEMPLATE_PARTIAL_SPECIALIZATION,
    NAMESPACE_ALIAS,
    USING_DIRECTIVE,
    USING_DECLARATION,
    TYPE_ALIAS_DECL,
    OBJC_SYNTHESIZE_DECL,
    OBJC_DYNAMIC_DECL,
    CXX_ACCESS_SPECIFIER,

    OBJC_SUPER_CLASS_REF,
    OBJC_PROTOCOL_REF,
    OBJC_CLASS_REF,
    TYPE_REF,
    CXX_BASE_SPECIFIER,
    TEMPLATE_REF,
    NAMESPACE_REF,
    MEMBER_REF,
    LABEL_REF,
    OVERLOADED_DECL_REF,
    VARIABLE_REF,

    INVALID_FILE,
    NO_DECL_FOUND,
    NOT_IMPLEMENTED,
    INVALID_CODE,

    UNEXPOSED_EXPR,
    DECL_REF_EXPR,
    MEMBER_REF_EXPR,
    CALL_EXPR,
    OBJC_MESSAGE_EXPR,
    BLOCK_EXPR,
    INTEGER_LITERAL,
    FLOATING_LITERAL,
    IMAGINARY_LITERAL,
    STRING_LITERAL,
    CHARACTER_LITERAL,
    PAREN_EXPR,
    UNARY_OPERATOR,
    ARRAY_SUBSCRIPT_EXPR,
    BINARY_OPERATOR,
    COMPOUND_ASSIGN_OPERATOR,
    CONDITIONAL_OPERATOR,
    C_STYLE_CAST_EXPR,
    COMPOUND_LITERAL_EXPR,
    INIT_LIST_EXPR,
    ADDR_LABEL_EXPR,
    STMT_EXPR,
    GENERIC_SELECTION_EXPR,
    GNU_NULL_EXPR,
    CXX_STATIC_CAST_EXPR,
    CXX_DYNAMIC_CAST_EXPR,
    CXX_REINTERPRET_CAST_EXPR,
    CXX_CONST_CAST_EXPR,
    CXX_FUNCTIONAL_CAST_EXPR,
    CXX_TYPEID_EXPR,
    CXX_BOOL_LITERAL_EXPR,
    CXX_NULL_PTR_LITERAL_EXPR,
    CXX_THIS_EXPR,
    CXX_THROW_EXPR,
    CXX_NEW_EXPR,
    CXX_DELETE_EXPR,
    UNARY_EXPR,
    OBJC_STRING_LITERAL,
    OBJC_ENCODE_EXPR,
    OBJC_SELECTOR_EXPR,
    OBJC_PROTOCOL_EXPR,
    OBJC_BRIDGED_CAST_EXPR,
    PACK_EXPANSION_EXPR,
    SIZE_OF_PACK_EXPR,
    LAMBDA_EXPR,
    OBJC_BOOL_LITERAL_EXPR,

    UNEXPOSED_STMT,
    LABEL_STMT,
    COMPOUND_STMT,
    CASE_STMT,
    DEFAULT_STMT,
    IF_STMT,
    SWITCH_STMT,
    WHILE_STMT,
    DO_STMT,
    FOR_STMT,
    GOTO_STMT,
    INDIRECT_GOTO_STMT,
    CONTINUE_STMT,
    BREAK_STMT,
    RETURN_STMT,
    // GCC_ASM_STMT,
    ASM_STMT,
    OBJC_AT_TRY_STMT,
    OBJC_AT_CATCH_STMT,
    OBJC_AT_FINALLY_STMT,
    OBJC_AT_THROW_STMT,
    OBJC_AT_SYNCHRONIZED_STMT,
    OBJC_AUTORELEASE_POOL_STMT,
    OBJC_FOR_COLLECTION_STMT,
    CXX_CATCH_STMT,
    CXX_TRY_STMT,
    CXX_FOR_RANGE_STMT,
    SEH_TRY_STMT,
    SEH_EXCEPT_STMT,
    SEH_FINALLY_STMT,
    MS_ASM_STMT,
    NULL_STMT,
    DECL_STMT,

    TRANSLATION_UNIT,
    UNEXPOSED_ATTR,
    IB_ACTION_ATTR,
    IB_OUTLET_ATTR,
    IB_OUTLET_COLLECTION_ATTR,
    CXX_FINAL_ATTR,
    CXX_OVERRIDE_ATTR,
    ANNOTATE_ATTR,
    ASM_LABEL_ATTR,

    PREPROCESSING_DIRECTIVE,
    MACRO_DEFINITION,
    MACRO_EXPANSION,
    // MACRO_INSTANTIATION,
    INCLUSION_DIRECTIVE,

    MODULE_IMPORT_DECL;

    private static final Map<Integer, CursorKind> FROM_NATIVE = new HashMap<Integer, CursorKind>();
    private static final Map<CursorKind, Integer> TO_NATIVE = new EnumMap<CursorKind, Integer>(CursorKind.class);

    static {
        // The code below depends on the actual CXCursorKind enum values
        int nativeValue = 1;
        for (CursorKind enumValue : values()) {
            switch (enumValue) {
                case INVALID_FILE: nativeValue = 70; break;
                case UNEXPOSED_EXPR: nativeValue = 100; break;
                case UNEXPOSED_STMT: nativeValue = 200; break;
                case TRANSLATION_UNIT: nativeValue = 300; break;
                case UNEXPOSED_ATTR: nativeValue = 400; break;
                case PREPROCESSING_DIRECTIVE: nativeValue = 500; break;
                case MODULE_IMPORT_DECL: nativeValue = 600; break;
            }
            FROM_NATIVE.put(nativeValue, enumValue);
            TO_NATIVE.put(enumValue, nativeValue);
            nativeValue++;
        }
    }

    @NotNull
    /* package */ static CursorKind fromNative(int kind) {
        CursorKind result = FROM_NATIVE.get(kind);
        if (result == null) {
            throw new IllegalStateException("Unknown CXCursorKind value: " + kind + ". Probably an incompatible libclang version");
        }
        return result;
    }

    /* package */ int toNative() {
        Integer result = TO_NATIVE.get(this);
        if (result == null) {
            throw new IllegalStateException("No corresponding CXCursorKind value: " + this + ". Probably an incompatible libclang version");
        }
        return result;
    }

    @NotNull
    public String getSpelling() {
        CXString.ByValue spelling = LibClang.I.getCursorKindSpelling(toNative());
        // TODO: dealloc
        String str = LibClang.I.getCString(spelling);
        return str;
    }
}
