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
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Joined;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link PngRaw}.
 *
 * @since 0.0.1
 */
final class PngTest {
    @Test
    void header() throws Exception {
        new Assertion<>(
            "Must represent a PNG header",
            new PngRaw(
                new Serial(),
                new BytesOf(new ResourceOf("image/logo.png"))
            ).header(),
            new IsText(
                new Joined(
                    "\n",
                    "Length: 13",
                    "Width: 104",
                    "Height: 71",
                    "Depth: 8",
                    "ColorType: 3",
                    "ColorSpace: Indexed",
                    "Compression: 0",
                    "Filter: 0",
                    "Interlacing: 0"
                )
            )
        ).affirm();
    }

    @Test
    void body() throws Exception {
        new Assertion<>(
            "Must represent a PNG body",
            new PngRaw(
                new Serial(),
                new BytesOf(new ResourceOf("image/logo.png"))
            ).body().asStream().length,
            new IsNumber(2086)
        ).affirm();
    }

    @Test
    void palette() throws Exception {
        new Assertion<>(
            "Must represent a PNG palette",
            new PngRaw(
                new Serial(),
                new BytesOf(new ResourceOf("image/logo.png"))
            ).palette().asStream().length,
            new IsNumber(192)
        ).affirm();
    }

    @Test
    void content() throws Exception {
        final String filename = "image/logo.png";
        final Id id = new Serial();
        final Png png = new Png(id, new BytesOf(new ResourceOf(filename)));
        final Raw raw = new PngRaw(id, new BytesOf(new ResourceOf(filename)));
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write(
            new Concatenated(
                "6 0 obj\n<< /Type /XObject /Subtype /Image /Width 104 ",
                "/Height 71 /ColorSpace [/Indexed /DeviceRGB 63 4 0 R] ",
                "/BitsPerComponent 8 /Filter /FlateDecode /DecodeParms << ",
                "/Predictor 15 /Colors 1 /BitsPerComponent 8 /Columns 104 ",
                ">> /Mask [0 0] /Length 2086 >>\nstream\n"
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        expected.write(raw.body().asStream());
        expected.write(
            "\nendstream\nendobj\n4 0 obj\n<< /Length 192 >>\nstream\n"
                .getBytes(StandardCharsets.UTF_8)
        );
        expected.write(raw.palette().asStream());
        expected.write(
            "\nendstream\nendobj\n".getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "Must represent a PNG content",
            expected.toByteArray(),
            new IsEqual<>(png.indirect().asBytes())
        ).affirm();
    }
}
