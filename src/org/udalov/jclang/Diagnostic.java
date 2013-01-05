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
import org.udalov.jclang.structs.CXString;
import org.udalov.jclang.util.Util;

public class Diagnostic extends PointerType {
    public enum DisplayOptions {
        DISPLAY_SOURCE_LOCATION,
        DISPLAY_COLUMN,
        DISPLAY_SOURCE_RANGES,
        DISPLAY_OPTION,
        DISPLAY_CATEGORY_ID,
        DISPLAY_CATEGORY_NAME
    }

    public enum Severity {
        IGNORED,
        NOTE,
        WARNING,
        ERROR,
        FATAL
    }

    @NotNull
    public String format(@NotNull DisplayOptions... options) {
        // TODO: dealloc
        int flags = Util.buildOptionsMask(options);
        CXString string = LibClang.I.formatDiagnostic(this, flags);
        return LibClang.I.getCString(string);
    }

    @NotNull
    public Severity getSeverity() {
        int severity = LibClang.I.getDiagnosticSeverity(this);
        return Severity.values()[severity];
    }
}
