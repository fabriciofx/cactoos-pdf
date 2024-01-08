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

import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.text.Concatenated;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link Resources}.
 *
 * @since 0.0.1
 */
final class ResourcesTest {
    @Test
    void resourcesDictionary() throws Exception {
        new Assertion<>(
            "Must represent a resources dictionary",
            new Resources(
                new ProcSet(),
                new TimesRoman(12),
                new XObject(
                    new Image(
                        "I1",
                        new Png("src/test/resources/image/logo.png"),
                        28,
                        766
                    )
                )
            ).indirect(new Serial()).dictionary(),
            new IsText(
                new Concatenated(
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    " /Font << /F1 3 0 R >> /XObject << /I1 5 0 R >> >>"
                )
            )
        ).affirm();
    }

    @Test
    void resourcesAsBytes() throws Exception {
        new Assertion<>(
            "Must represent a resources as bytes",
            new TextOf(
                new Resources(
                    new ProcSet(),
                    new TimesRoman(12),
                    new XObject(
                        new Image(
                            "I1",
                            new Png("src/test/resources/image/logo.png"),
                            28,
                            766
                        )
                    )
                ).indirect(new Serial()).asBytes()
            ),
            new AllOf<>(
                new StartsWith(
                    new Concatenated(
                        "2 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 3 0 R >> /XObject << /I1 5 0 R >> >>\nendobj\n",
                        "3 0 obj\n<< /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >>\nendobj\n",
                        "4 0 obj\n<< /XObject << /I1 5 0 R >> >>\nendobj\n",
                        "5 0 obj\n<< /Type /XObject /Subtype /Image /Width 104 /Height 71 /ColorSpace [/Indexed /DeviceRGB 63 7 0 R] /BitsPerComponent 8 /Filter /FlateDecode /DecodeParms << /Predictor 15 /Colors 1 /BitsPerComponent 8 /Columns 104 >> /Mask [0 0] /Length 2086 >>\nstream\n"
                    )
                ),
                new EndsWith(
                    "\nendstream\nendobj\n"
                )
            )
        ).affirm();
    }

    @Test
    void resourcesDictionaryWithTwoFonts() throws Exception {
        new Assertion<>(
            "Must represent a resources dictionary",
            new Resources(
                new ProcSet(),
                new TimesRoman(12),
                new Helvetica(12)
            ).indirect(new Serial()).dictionary(),
            new IsText(
                new Concatenated(
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    " /Font << /F1 3 0 R /F2 4 0 R >> >>"
                )
            )
        ).affirm();
    }
}
