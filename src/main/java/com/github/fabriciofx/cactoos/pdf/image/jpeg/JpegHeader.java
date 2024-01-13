/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023-2024 Fabrício Barros Cabral
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
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Color;
import com.github.fabriciofx.cactoos.pdf.image.Flow;
import com.github.fabriciofx.cactoos.pdf.image.Header;
import java.util.Locale;
import org.cactoos.Bytes;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;

/**
 * JpegHeader.
 *
 * @since 0.0.1
 *
 * FF C0        // Start of Frame marker
 * XX XX        // Length of the segment (excluding marker and length fields)
 * XX           // Data precision (usually 8 bits per component)
 * XX XX        // Image height (2 bytes)
 * XX XX        // Image width (2 bytes)
 * XX           // Number of components (usually 3 for color images)
 * XX           // Component ID (1 byte)
 * XX           // Horizontal sampling factor (4 bits)
 * XX           // Vertical sampling factor (4 bits)
 * XX           // Quantization table ID (1 byte)
 * ... (repeat the last three lines for each component)
 */
public final class JpegHeader implements Header {
    /**
     * JPEG header bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     *
     * @param bytes JPEG header bytes
     */
    public JpegHeader(final Bytes bytes) {
        this.bytes = bytes;
    }

    @Override
    public int length() throws Exception {
        throw new UnsupportedOperationException(
            "I can't perform length() in a JPEG header"
        );
    }

    @Override
    public int width() throws Exception {
        final Flow flow = new Flow(this.bytes.asBytes());
        flow.search(new byte[]{(byte) 0xff, (byte) 0xc0});
        flow.skip(5);
        return flow.asShort();
    }

    @Override
    public int height() throws Exception {
        final Flow flow = new Flow(this.bytes.asBytes());
        flow.search(new byte[]{(byte) 0xff, (byte) 0xc0});
        flow.skip(3);
        return flow.asShort();
    }

    @Override
    public int depth() throws Exception {
        final Flow flow = new Flow(this.bytes.asBytes());
        flow.search(new byte[]{(byte) 0xff, (byte) 0xc0});
        flow.skip(2);
        return Math.max(flow.asByte(), 8);
    }

    @Override
    public Color color() throws Exception {
        final Flow flow = new Flow(this.bytes.asBytes());
        flow.search(new byte[]{(byte) 0xff, (byte) 0xc0});
        flow.skip(7);
        return new JpegColor(flow.asByte());
    }

    @Override
    public int compression() throws Exception {
        throw new UnsupportedOperationException(
            "I can't perform compression() in a JPEG header"
        );
    }

    @Override
    public int filter() throws Exception {
        throw new UnsupportedOperationException(
            "I can't perform filter() in a JPEG header"
        );
    }

    @Override
    public int interlacing() throws Exception {
        throw new UnsupportedOperationException(
            "I can't perform interlacing() in a JPEG header"
        );
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.bytes.asBytes();
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            new Joined(
                "\n",
                "Width: %d",
                "Height: %d",
                "Depth: %d",
                "Color: %s"
            ),
            Locale.ENGLISH,
            this.width(),
            this.height(),
            this.depth(),
            this.color().space()
        ).asString();
    }
}
