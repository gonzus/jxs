package io.crossroads;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class XsPollItem
    extends Structure {
    
    public Pointer socket;
    public Pointer fd;
    public short events;
    public short revents;

    public XsPollItem() {
        super();
    }

    public XsPollItem(Pointer socket,
                      Pointer fd,
                      short events)
    {
        super();
        this.socket = socket;
        this.fd = fd;
        this.events = events;
        this.revents = 0;
    }

    public static class ByReference
        extends XsPollItem
        implements Structure.ByReference {
    }

    public static class ByValue
        extends XsPollItem
        implements Structure.ByValue {
    }
}
