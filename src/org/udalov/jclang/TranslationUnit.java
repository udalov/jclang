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

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit extends PointerType {
    public enum Flag {
        DETAILED_PREPROCESSING_RECORD,
        INCOMPLETE,
        PRECOMPILED_PREAMBLE,
        CACHE_COMPLETION_RESULTS,
        FOR_SERIALIZATION,
        @Deprecated CXX_CHAINED_PCH,
        SKIP_FUNCTION_BODIES,
        INCLUDE_BRIEF_COMMENTS_IN_CODE_COMPLETION
    }

    @NotNull
    public List<Diagnostic> getDiagnostics() {
        int n = LibClang.I.getNumDiagnostics(this);
        List<Diagnostic> result = new ArrayList<Diagnostic>(n);
        for (int i = 0; i < n; i++) {
            Diagnostic diagnostic = LibClang.I.getDiagnostic(this, i);
            result.add(NativePool.I.record(diagnostic));
        }
        return result;
    }
}
