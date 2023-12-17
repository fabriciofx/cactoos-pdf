package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.PngImage;
import org.cactoos.text.FormattedText;

public class XObject implements Resource {
    private final String name;
    private final PngImage png;

    public XObject(final String name, final PngImage png) {
        this.name = name;
        this.png = png;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return new FormattedText(
            "/XObject << /%s %s >>",
            this.name,
            this.png.reference()
        ).asString().getBytes();
    }
}
