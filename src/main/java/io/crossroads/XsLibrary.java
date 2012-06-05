package io.crossroads;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;

public interface XsLibrary extends Library {
    void xs_version(int[] major, int[] minor, int[] patch);
}
