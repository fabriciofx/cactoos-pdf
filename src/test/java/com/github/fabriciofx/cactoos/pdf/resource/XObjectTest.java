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
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link XObject}.
 *
 * @since 0.0.1
 */
final class XObjectTest {
    @Test
    void xobjectDictionary() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must print XObject dictionary",
            new XObject(
                id,
                new Image(
                    id,
                    new Png(
                        id,
                        new BytesOf(new ResourceOf("image/logo.png"))
                    ),
                    28,
                    766
                )
            ).indirect().dictionary(),
            new IsText("<< /XObject << /I3 1 0 R >> >>")
        ).affirm();
    }

    @Test
    void xobjectAsBytes() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must print XObject as bytes",
            new TextOf(
                new XObject(
                    id,
                    new Image(
                        id,
                        new Png(
                            id,
                            new BytesOf(new ResourceOf("image/logo.png"))
                        ),
                        28,
                        766
                    )
                ).indirect().asBytes()
            ),
            new IsText(
                "5 0 obj\n<< /XObject << /I3 1 0 R >> >>\nendobj\n"
            )
        ).affirm();
    }
}
