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
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link com.github.fabriciofx.cactoos.pdf.Page}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength"})
final class PageTest {
    @Test
    void buildDocumentWithRotatePage() {
        final Id id = new Serial();
        new Assertion<>(
            "Must represent a PDF document with rotated page",
            new TextOf(
                new Document(
                    id,
                    new DefaultPages(
                        id,
                        new Rotate90(
                            new DefaultPage(
                                id,
                                new Contents(
                                    new Text(
                                        id,
                                        new TimesRoman(id, 18),
                                        0,
                                        0,
                                        new TextOf("Hello World!")
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            new IsText(
                new Joined(
                    "\n",
                    "%PDF-1.3\n%���������",
                    "5 0 obj\n<< /Producer (cactoos-pdf) >>\nendobj",
                    "6 0 obj\n<< /Type /Catalog /Pages 4 0 R >>\nendobj",
                    "4 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "3 0 obj\n<< /Type /Page /Resources 7 0 R /Contents [2 0 R] /Parent 4 0 R /Rotate 90 >>\nendobj",
                    "7 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 1 0 R >> >>\nendobj",
                    "1 0 obj\n<< /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >>\nendobj",
                    "2 0 obj\n<< /Length 58 >>\nstream\nBT /F1 18 Tf 0.00 0.00 Td 21.60 TL\n(Hello World!) Tj T*\nET\nendstream\nendobj",
                    "trailer << /Root 6 0 R /Size 8 /Info 5 0 R >>",
                    "%%EOF"
                )
            )
        ).affirm();
    }

    @Test
    void buildTwoPagesDocument() {
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 18);
        new Assertion<>(
            "Must represent a two pages PDF document",
            new TextOf(
                new Document(
                    id,
                    new DefaultPages(
                        id,
                        new DefaultPage(
                            id,
                            new Contents(
                                new Text(
                                    id,
                                    font,
                                    0,
                                    0,
                                    new TextOf("Hello")
                                )
                            )
                        ),
                        new DefaultPage(
                            id,
                            new Contents(
                                new Text(
                                    id,
                                    font,
                                    0,
                                    0,
                                    new TextOf("World")
                                )
                            )
                        )
                    )
                )
            ),
            new IsText(
                new Joined(
                    "\n",
                    "%PDF-1.3\n%���������",
                    "7 0 obj\n<< /Producer (cactoos-pdf) >>\nendobj",
                    "8 0 obj\n<< /Type /Catalog /Pages 6 0 R >>\nendobj",
                    "6 0 obj\n<< /Type /Pages /Kids [3 0 R 5 0 R] /Count 2 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "3 0 obj\n<< /Type /Page /Resources 9 0 R /Contents [2 0 R] /Parent 6 0 R >>\nendobj",
                    "9 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 1 0 R >> >>\nendobj",
                    "1 0 obj\n<< /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >>\nendobj",
                    "2 0 obj\n<< /Length 51 >>\nstream\nBT /F1 18 Tf 0.00 0.00 Td 21.60 TL\n(Hello) Tj T*\nET\nendstream\nendobj",
                    "5 0 obj\n<< /Type /Page /Resources 10 0 R /Contents [4 0 R] /Parent 6 0 R >>\nendobj",
                    "10 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 1 0 R >> >>\nendobj",
                    "4 0 obj\n<< /Length 51 >>\nstream\nBT /F1 18 Tf 0.00 0.00 Td 21.60 TL\n(World) Tj T*\nET\nendstream\nendobj",
                    "trailer << /Root 8 0 R /Size 11 /Info 7 0 R >>",
                    "%%EOF"
                )
            )
        ).affirm();
    }
}
