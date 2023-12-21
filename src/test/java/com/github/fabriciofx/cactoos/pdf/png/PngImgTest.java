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

import com.github.fabriciofx.cactoos.pdf.count.ObjectCount;
import org.cactoos.text.Joined;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link PngImg}.
 *
 * @since 0.0.1
 */
final class PngImgTest {
    @Test
    void header() throws Exception {
        new Assertion<>(
            "Must represent a PNG header",
            new PngImg(
                new ObjectCount(),
                "src/test/resources/image/logo.png"
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
            "Must represent a PNG palette",
            new PngImg(
                new ObjectCount(),
                "src/test/resources/image/logo.png"
            ).body().stream().length,
            new IsNumber(2086)
        ).affirm();
    }

    @Test
    void palette() throws Exception {
        new Assertion<>(
            "Must represent a PNG palette",
            new PngImg(
                new ObjectCount(),
                "src/test/resources/image/logo.png"
            ).palette().stream().length,
            new IsNumber(192)
        ).affirm();
    }
}
