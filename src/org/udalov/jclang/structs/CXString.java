package org.udalov.jclang.structs;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class CXString extends Structure {
    @SuppressWarnings("unused")
    public Pointer data;
    @SuppressWarnings("unused")
    public int privateFlags;

    public CXString() {
        super();
        setFieldOrder(new String[]{"data", "privateFlags"});
    }
}
