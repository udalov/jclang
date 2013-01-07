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

import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.NotNull;
import org.udalov.jclang.structs.CXIdxLoc;

import java.io.File;

public class IndexLocation {
    private final CXIdxLoc.ByValue location;

    private boolean isComputed;
    private File file;
    private int line;
    private int column;
    private int offset;

    public IndexLocation(@NotNull CXIdxLoc.ByValue location) {
        this.location = location;
    }

    private void retrieveLocation() {
        if (isComputed) return;
        isComputed = true;

        PointerByReference file = new PointerByReference();
        IntByReference line = new IntByReference();
        IntByReference column = new IntByReference();
        IntByReference offset = new IntByReference();
        LibClang.I.indexLoc_getFileLocation(location, null, file, line, column, offset);

        CXFile cxFile = new CXFile();
        cxFile.setPointer(file.getValue());
        this.file = cxFile.toFile();
        this.line = line.getValue();
        this.column = column.getValue();
        this.offset = offset.getValue();
    }

    // TODO: probably is null for unsaved files, investigate
    @NotNull
    public File getFile() {
        retrieveLocation();
        return file;
    }

    public int getLine() {
        retrieveLocation();
        return line;
    }

    public int getColumn() {
        retrieveLocation();
        return column;
    }

    public int getOffset() {
        retrieveLocation();
        return offset;
    }

    @NotNull
    public String toString() {
        return getFile() + "[" + getOffset() + "]:" + getLine() + ":" + getColumn();
    }
}
