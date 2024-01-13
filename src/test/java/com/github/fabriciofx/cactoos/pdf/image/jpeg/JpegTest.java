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

import com.github.fabriciofx.cactoos.pdf.id.Serial;
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
            new Serial(),
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
            new Serial(),
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
            new Serial(),
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
            new Serial(),
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
                new Serial(),
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
                new Serial(),
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
            new Serial(),
            new BytesOf(new ResourceOf("image/sample-1.jpg"))
        );
        new Assertion<>(
            "Must represent the height of a safe JPEG image",
            raw.body().asStream().length,
            new IsNumber(69_325)
        ).affirm();
    }
}
