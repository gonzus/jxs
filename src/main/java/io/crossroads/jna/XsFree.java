package io.crossroads.jna;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class XsFree
    extends PointerType {
    
    public XsFree(Pointer address) {
        super(address);
    }

    public XsFree() {
        super();
    }
}
