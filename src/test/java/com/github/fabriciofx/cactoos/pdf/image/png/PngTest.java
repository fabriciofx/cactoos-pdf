/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.png;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.Raw;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
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
                "1 0 obj\n<< /Type /XObject /Subtype /Image /Width 104 ",
                "/Height 71 /ColorSpace [/Indexed /DeviceRGB 63 2 0 R] ",
                "/BitsPerComponent 8 /Filter /FlateDecode /DecodeParms << ",
                "/Predictor 15 /Colors 1 /BitsPerComponent 8 /Columns 104 ",
                ">> /Mask [0 0] /Length 2086 >>\nstream\n"
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        expected.write(raw.body().asStream());
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
