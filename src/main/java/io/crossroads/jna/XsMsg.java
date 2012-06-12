package io.crossroads.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class XsMsg
    extends Structure {
    
    public Pointer content;
    public byte flags;
    public byte vsm_size;
    public byte[] vsm_data = new byte[30];

    public XsMsg() {
        super();
    }

    public static class ByReference
        extends XsMsg
        implements Structure.ByReference {
    }

    public static class ByValue
        extends XsMsg
        implements Structure.ByValue {
    }
}
