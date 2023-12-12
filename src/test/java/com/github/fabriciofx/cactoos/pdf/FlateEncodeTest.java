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
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.content.Text;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link FlateEncode}.
 *
 * @since 0.0.1
 */
final class FlateEncodeTest {
    @Test
    void encode() throws Exception {
        final Content content = new Text(
            new ObjectCount(),
            18,
            0,
            0,
            new TextOf("Hello World!")
        );
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        final Deflater deflater = new Deflater();
        try (
            DeflaterOutputStream out = new DeflaterOutputStream(
                expected,
                deflater
            )
        ) {
            out.write(content.stream());
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new FlateEncode(content).stream());
        deflater.end();
        new Assertion<>(
            "Must encode a content using Flate algorithm",
            baos.toByteArray(),
            new IsEqual<>(expected.toByteArray())
        ).affirm();
    }
}
