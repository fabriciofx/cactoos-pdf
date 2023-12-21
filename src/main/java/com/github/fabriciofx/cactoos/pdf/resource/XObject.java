package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.content.PngImage;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Text;

public class XObject implements Resource {
    private final String name;
    private final PngImage png;

    public XObject(final String name, final PngImage png) {
        this.name = name;
        this.png = png;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.dictionary().asBytes();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        return new Dictionary()
            .add(
                "XObject",
                new Dictionary().add(
                    this.name,
                    new Text(this.png.reference().asString())
                )
            );
    }
}
