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
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Serial;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Resources}.
 *
 * @since 0.0.1
 */
final class ResourcesTest {
    @Test
    void definition() throws Exception {
        final Png png = new Png(
            "src/test/resources/image/logo.png"
        );
        final Image image = new Image(
            "I1",
            png,
            28,
            766
        );
        new Assertion<>(
            "Must represent a resources definition",
            new TextOf(
                new Resources(
                    new ProcSet(),
                    new Font(
                        new FontFamily("Times-Roman", "Type1"),
                        "F1",
                        12
                    ),
                    new XObject(image)
                ).definition(new Serial())
            ),
            new IsText(
                new Joined(
                    " ",
                    "1 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    "/Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype",
                    "/Type1 >> >> /XObject << /I1 3 0 R >> >>\nendobj\n"
                )
            )
        ).affirm();
    }
}
