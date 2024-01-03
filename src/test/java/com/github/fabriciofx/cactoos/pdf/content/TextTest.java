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

import com.github.fabriciofx.cactoos.pdf.Serial;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.font.FontEnvelope;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Text}.
 *
 * @since 0.0.1
 */
final class TextTest {
    @Test
    void oneline() throws Exception {
        new Assertion<>(
            "Must don't break a small text",
            new TextOf(
                new Text(
                    new TimesRoman(18),
                    0,
                    0,
                    50,
                    20,
                    new TextOf("Hello World")
                ).indirect(new Serial(1))
            ),
            new IsText(
                new Joined(
                    "\n",
                    "1 0 obj",
                    "<< /Length 57 >>",
                    "stream",
                    "BT /F1 18 Tf 0.00 0.00 Td 20.00 TL",
                    "(Hello World) Tj T*",
                    "ET",
                    "endstream",
                    "endobj\n"
                )
            )
        ).affirm();
    }

    @Test
    void multiLines() throws Exception {
        new Assertion<>(
            "Must break a big text into multiline",
            new TextOf(
                new Text(
                    new TimesRoman(18),
                    0,
                    0,
                    50,
                    20,
                    new Joined(
                        " ",
                        "Lorem ea et aliquip culpa aute",
                        "amet elit nostrud culpa veniam",
                        "dolore eu irure incididunt.",
                        "Velit officia occaecat est",
                        "adipisicing mollit veniam.",
                        "Minim sunt est culpa labore.",
                        "Ut culpa et nulla sunt labore",
                        "aliqua ipsum laborum nostrud sit",
                        "deserunt officia labore. Sunt",
                        "laboris id labore sit ex. Eiusmod",
                        "nulla eu incididunt excepteur",
                        "minim officia dolore veniam",
                        "labore enim quis reprehenderit.",
                        "Magna in laboris irure enim non",
                        "deserunt laborum mollit labore",
                        "id amet."
                    )
                ).indirect(new Serial(1))
            ),
            new IsText(
                new Joined(
                    "\n",
                    "1 0 obj",
                    "<< /Length 579 >>",
                    "stream",
                    "BT /F1 18 Tf 0.00 0.00 Td 20.00 TL",
                    "(Lorem ea et aliquip culpa aute amet elit nostrud) Tj T*",
                    "(culpa veniam dolore eu irure incididunt. Velit) Tj T*",
                    "(officia occaecat est adipisicing mollit veniam.) Tj T*",
                    "(Minim sunt est culpa labore. Ut culpa et nulla) Tj T*",
                    "(sunt labore aliqua ipsum laborum nostrud sit) Tj T*",
                    "(deserunt officia labore. Sunt laboris id labore) Tj T*",
                    "(sit ex. Eiusmod nulla eu incididunt excepteur) Tj T*",
                    "(minim officia dolore veniam labore enim quis) Tj T*",
                    "(reprehenderit. Magna in laboris irure enim non) Tj T*",
                    "(deserunt laborum mollit labore id amet.) Tj T*",
                    "ET",
                    "endstream",
                    "endobj\n"
                )
            )
        ).affirm();
    }
}
