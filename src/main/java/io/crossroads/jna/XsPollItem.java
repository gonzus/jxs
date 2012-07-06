package io.crossroads.jna;

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
        this.socket = null;
        this.fd = null;
        this.events = 0;
        this.revents = 0;
    }

    public XsPollItem(Pointer socket,
                      Pointer fd,
                      short events)
    {
        this();
        this.socket = socket;
        this.fd = fd;
        this.events = events;
    }

    public XsPollItem(Pointer socket,
                      short events)
    {
        this(socket, null, events);
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
