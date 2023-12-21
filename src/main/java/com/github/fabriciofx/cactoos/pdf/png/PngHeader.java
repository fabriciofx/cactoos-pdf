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
package com.github.fabriciofx.cactoos.pdf.png;

import java.util.Arrays;
import org.cactoos.Bytes;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;

/**
 * PNG Image Header
 *
 * PNG Image Header Structure:
 * ----------------------------
 *
 * Offset  Length   Field Name       Description
 * ---------------------------------------------
 * 0       8 bytes  Signature        PNG file signature (137 80 78 71 13 10 26 10)
 * 8       4 bytes  Chunk Length     Length of the chunk data (excluding length and type)
 * 12      4 bytes  Chunk Type       Type of the chunk (e.g., IHDR for image header)
 * 16      4 bytes  Width            Width of the image in pixels
 * 20      4 bytes  Height           Height of the image in pixels
 * 24      1 byte   Bit Depth        Number of bits per sample
 * 25      1 byte   Color Type       Type of color encoding used
 * 26      1 byte   Compression      Compression method used
 * 27      1 byte   Filter Method    Filtering method used
 * 28      1 byte   Interlace Method Interlace method used
 *
 * Additional chunks (not included in the header):
 * - Data chunks (IDAT) containing the image data
 * - Palette chunks (PLTE) for indexed-color images
 * - Transparency chunks (tRNS) for specifying transparency
 * - Textual information chunks (tEXt, iTXt) for metadata
 * - Other optional chunks for various purposes
 */
public final class PngHeader implements Header {
    private final Bytes bytes;

    public PngHeader(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public int length() throws Exception {
        return new BytesAsInteger(
            Arrays.copyOfRange(this.bytes.asBytes(), 8, 12)
        ).value();
    }

    @Override
    public int width() throws Exception {
        return new BytesAsInteger(
            Arrays.copyOfRange(this.bytes.asBytes(), 16, 20)
        ).value();
    }

    @Override
    public int height() throws Exception {
        return new BytesAsInteger(
            Arrays.copyOfRange(this.bytes.asBytes(), 20, 24)
        ).value();
    }

    @Override
    public int depth() throws Exception {
        return this.bytes.asBytes()[24];
    }

    @Override
    public Color color() throws Exception {
        return new Color(this.bytes.asBytes()[25]);
    }

    @Override
    public int compression() throws Exception {
        return this.bytes.asBytes()[26];
    }

    @Override
    public int filter() throws Exception {
        return this.bytes.asBytes()[27];
    }

    @Override
    public int interlacing() throws Exception {
        return this.bytes.asBytes()[28];
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            new Joined(
                "\n",
                "Length: %d",
                "Width: %d",
                "Height: %d",
                "Depth: %d",
                "ColorType: %d",
                "ColorSpace: %s",
                "Compression: %d",
                "Filter: %d",
                "Interlacing: %d"
            ),
            this.length(),
            this.width(),
            this.height(),
            this.depth(),
            this.color().type(),
            this.color().space(),
            this.compression(),
            this.filter(),
            this.interlacing()
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.bytes.asBytes();
    }
}
