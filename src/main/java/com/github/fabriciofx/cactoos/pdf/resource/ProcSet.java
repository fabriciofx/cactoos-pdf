package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Resource;

public final class ProcSet implements Resource {
    @Override
    public byte[] asBytes() throws Exception {
        return "/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]".getBytes();
    }
}
