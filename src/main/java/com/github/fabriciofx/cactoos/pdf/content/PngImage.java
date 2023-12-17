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
import com.github.fabriciofx.cactoos.pdf.Flow;
import com.github.fabriciofx.cactoos.pdf.Object;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import org.cactoos.Bytes;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.UncheckedText;

public final class PngImage implements Object, Bytes {
    private final int number;
    private final int generation;
    private final int paletteReference;
    private final Bytes raw;

    public PngImage(
        final Count count,
        final String filename
    ) {
        this(
            count.increment(),
            0,
            count.increment(),
            () -> Files.readAllBytes(new File(filename).toPath())
        );
    }

    public PngImage(
        final int number,
        final int generation,
        final int paletteReference,
        final Bytes raw
    ) {
        this.number = number;
        this.generation = generation;
        this.paletteReference = paletteReference;
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
        final Flow flow = new Flow(this.raw.asBytes());
        final byte[] signature = {
            (byte) 137, 'P', 'N', 'G', '\r', '\n', 26, '\n'
        };
        if (!Arrays.equals(signature, flow.asBytes(8))) {
            throw new Exception("Not a PNG image file");
        }
        flow.skip(4);
        if (!flow.asString(4).equals("IHDR")) {
            throw new Exception("Incorrect PNG image file");
        }
        final int width = flow.asInt();
        final int height = flow.asInt();
        final int bitsPerComponent = flow.asByte();
        if (bitsPerComponent > 8) {
            throw new Exception("16-bit depth in PNG file not supported");
        }
        final int colorType = flow.asByte();
        final String colorSpace;
        if (colorType == 0 || colorType == 4) {
            colorSpace = "DeviceGray";
        } else if (colorType == 2 || colorType == 6) {
            colorSpace = "DeviceRGB";
        } else if (colorType == 3) {
            colorSpace = "Indexed";
        } else {
            throw new Exception("Unknown color type");
        }
        final int compressionMethod = flow.asByte();
        if (compressionMethod != 0) {
            throw new Exception("Unknown compression method");
        }
        final int filterMethod = flow.asByte();
        if (filterMethod != 0) {
            throw new Exception("Unknown filter method");
        }
        final int interlacing = flow.asByte();
        if (interlacing != 0) {
            throw new Exception("Interlacing not supported");
        }
        flow.skip(4);
        final String dp = new FormattedText(
            "/Predictor 15 /Colors %d /BitsPerComponent %d /Columns %d",
            colorSpace.equals("DeviceRGB") ? 3 : 1,
            bitsPerComponent,
            width
        ).asString();
        int n;
        byte[] palette = new byte[0];
        byte[] trns = new byte[0];
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        do {
            n = flow.asInt();
            final String type = flow.asString(4);
            if (type.equals("PLTE")) {
                palette = flow.asBytes(n);
                flow.skip(4);
            } else if (type.equals("tRNS")) {
                final byte[] t = flow.asBytes(n);
                if (colorType == 0) {
                    trns = new byte[]{(byte) new String(t).charAt(1)};
                } else if (colorType == 2) {
                    trns = new byte[]{
                        (byte) new String(t).charAt(1),
                        (byte) new String(t).charAt(3),
                        (byte) new String(t).charAt(5)
                    };
                } else {
                    final int pos = new String(t).indexOf('0');
                    if (pos != -1) {
                        trns = new byte[]{(byte) pos};
                    }
                }
                flow.skip(4);
            } else if (type.equals("IDAT")) {
                data.write(flow.asBytes(n));
                flow.skip(4);
            } else if (type.equals("IEND")) {
                break;
            } else {
                flow.skip(n + 4);
            }
        } while (n > 0);
        if (colorSpace.equals("Indexed") && palette.length == 0) {
            throw new Exception("Missing palette in PNG file");
        }
        if (colorType >= 4) {
            System.out.println("alpha channel");
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Write the image (data) object
        baos.write(
            new FormattedText(
                new Joined(
                    "\n",
                    "%d %d obj",
                    "<< /Type /XObject",
                    "/Subtype /Image",
                    "/Width %d",
                    "/Height %d",
                    "/ColorSpace [/%s /DeviceRGB %d %d 0 R]",
                    "/BitsPerComponent %d",
                    "/Filter /FlateDecode",
                    "/DecodeParms << %s >>",
                    "/Mask [0 0]",
                    "/Length %d >>",
                    "stream\n"
                ),
                this.number,
                this.generation,
                width,
                height,
                colorSpace,
                palette.length / 3 - 1,
                this.paletteReference,
                bitsPerComponent,
                dp,
                data.toByteArray().length
            ).asString().getBytes()
        );
        baos.write(data.toByteArray());
        baos.write("\nendstream\nendobj\n".getBytes());
        // Write the palette object
        baos.write(
            new FormattedText(
                "%d %d obj\n<< /Length %d >>\nstream\n",
                this.paletteReference,
                0,
                palette.length
            ).asString().getBytes()
        );
        baos.write(palette);
        baos.write("\nendstream\nendobj\n".getBytes());
        return baos.toByteArray();
    }
}
