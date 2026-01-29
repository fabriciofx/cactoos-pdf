/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.image.BytesAsInteger;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import java.util.Arrays;
import java.util.Locale;
import org.cactoos.Bytes;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;

/**
 * PngHeader: PNG Image Header.
 *
 * @since 0.0.1
 *
 * PNG Image Header Structure:
 * ----------------------------
 *
 * Offset  Length   Field Name       Description
 * ---------------------------------------------
 * 0       8 bytes  Signature        PNG file signature (137 80 78 71 13 10 26
 *                                   10)
 * 8       4 bytes  Chunk Length     Length of the chunk data (excluding length
 *                                   and type)
 * 12      4 bytes  Chunk Type       Type of the chunk (e.g., IHDR for image
 *                                   header)
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
    /**
     * PNG header bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes PNG header bytes
     */
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
    public PngColor color() throws Exception {
        return new PngColor(this.bytes.asBytes()[25]);
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
            Locale.ENGLISH,
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
