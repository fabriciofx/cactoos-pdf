/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.image.jpeg;

import com.github.fabriciofx.cactoos.pdf.image.Raw;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link JpegRaw}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class JpegTest {
    @Test
    void headerWidth() throws Exception {
        final Raw raw = new JpegRaw(
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the width of a JPEG image",
            raw.header().width(),
            new IsNumber(640)
        ).affirm();
    }

    @Test
    void headerHeight() throws Exception {
        final Raw raw = new JpegRaw(
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the width of a JPEG image",
            raw.header().height(),
            new IsNumber(480)
        ).affirm();
    }

    @Test
    void headerDepth() throws Exception {
        final Raw raw = new JpegRaw(
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the width of a JPEG image",
            raw.header().depth(),
            new IsNumber(8)
        ).affirm();
    }

    @Test
    void headerColorSpace() throws Exception {
        final Raw raw = new JpegRaw(
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the width of a JPEG image",
            new TextOf(raw.header().color().space()),
            new IsText("DeviceRGB")
        ).affirm();
    }

    @Test
    void safeJpegWidth() throws Exception {
        final Raw raw = new Safe(
            new JpegRaw(
                new BytesOf(new ResourceOf("image/sample-1.jpg"))
            )
        );
        new Assertion<>(
            "Must represent the width of a safe JPEG image",
            raw.header().width(),
            new IsNumber(640)
        ).affirm();
    }

    @Test
    void safeJpegHeight() throws Exception {
        final Raw raw = new Safe(
            new JpegRaw(
                new BytesOf(new ResourceOf("image/sample-1.jpg"))
            )
        );
        new Assertion<>(
            "Must represent the height of a safe JPEG image",
            raw.header().height(),
            new IsNumber(480)
        ).affirm();
    }

    @Test
    void body() throws Exception {
        final Raw raw = new JpegRaw(
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the height of a safe JPEG image",
            raw.body().asStream().length,
            new IsNumber(69_325)
        ).affirm();
    }
}
