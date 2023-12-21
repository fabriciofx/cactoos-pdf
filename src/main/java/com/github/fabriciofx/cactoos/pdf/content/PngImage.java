/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Count;
import com.github.fabriciofx.cactoos.pdf.Object;
import com.github.fabriciofx.cactoos.pdf.png.Body;
import com.github.fabriciofx.cactoos.pdf.png.Header;
import com.github.fabriciofx.cactoos.pdf.png.Img;
import com.github.fabriciofx.cactoos.pdf.png.Palette;
import com.github.fabriciofx.cactoos.pdf.png.PngImg;
import com.github.fabriciofx.cactoos.pdf.png.SafePngImg;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.Bytes;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.UncheckedText;

public final class PngImage implements Object, Bytes {
    private final int number;
    private final int generation;
    private final Count count;
    private final Bytes raw;

    public PngImage(
        final Count count,
        final String filename
    ) {
        this(
            count.increment(),
            0,
            count,
            () -> Files.readAllBytes(new File(filename).toPath())
        );
    }

    public PngImage(
        final int number,
        final int generation,
        final Count count,
        final Bytes raw
    ) {
        this.number = number;
        this.generation = generation;
        this.count = count;
        this.raw = raw;
    }

    @Override
    public String reference() {
        return new UncheckedText(
            new FormattedText(
                "%d %d R",
                this.number,
                this.generation
            )
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final Img img = new SafePngImg(new PngImg(this.count, this.raw));
        final Header header = img.header();
        final Body body = img.body();
        final Palette palette = img.palette();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                new Joined(
                    "\n",
                    "%d %d obj",
                    "<< /Type /XObject",
                    "/Subtype /Image",
                    "/Width %d",
                    "/Height %d",
                    "/ColorSpace [/%s /DeviceRGB %d %s]",
                    "/BitsPerComponent %d",
                    "/Filter /FlateDecode",
                    "/DecodeParms << /Predictor 15 /Colors %d /BitsPerComponent %d /Columns %d >>",
                    "/Mask [0 0]",
                    "/Length %d >>",
                    "stream\n"
                ),
                this.number,
                this.generation,
                header.width(),
                header.height(),
                header.color().space(),
                palette.stream().length / 3 - 1,
                palette.reference(),
                header.depth(),
                header.color().space().equals("DeviceRGB") ? 3 : 1,
                header.depth(),
                header.width(),
                body.stream().length
            ).asString().getBytes()
        );
        baos.write(body.stream());
        baos.write("\nendstream\nendobj\n".getBytes());
        // Write the palette object
        baos.write(palette.asBytes());
        return baos.toByteArray();
    }
}
