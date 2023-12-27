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
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Catalog;
import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Information;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Font;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.text.Date;
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
    void buildDocumentWithRotatePage() throws Exception {
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        new Assertion<>(
            "Must represent a PDF document",
            new TextOf(
                new Document(
                    new Information(
                        "Title", "Hello World",
                        "Subject", "PDF document",
                        "Author", "Fabricio Cabral",
                        "Creator", "cactoos-pdf",
                        "Producer", "cactoos-pdf",
                        "CreationDate", date.asString(),
                        "ModDate", date.asString(),
                        "Keywords", "cactoos pdf elegant objects"
                    ),
                    new Catalog(
                        new DefaultPages(
                            PageFormat.A4,
                            new Rotate(
                                new DefaultPage(
                                    new Resources(
                                        new Font(
                                            new FontFamily(
                                                "Times-Roman",
                                                "Type1"
                                            ),
                                            "F1"
                                        )
                                    ),
                                    new Contents(
                                        new Text(
                                            18,
                                            0,
                                            0,
                                            new TextOf(
                                                "Hello World!"
                                            )
                                        )
                                    )
                                ),
                                90
                            )
                        )
                    )
                )
            ),
            new IsText(
                new Joined(
                    "\n",
                    "%PDF-1.3\n%���������",
                    "1 0 obj\n<< /Title (Hello World) /Subject (PDF document) /Author (Fabricio Cabral) /Creator (cactoos-pdf) /Producer (cactoos-pdf) /CreationDate (D:20231211201132+03'00') /ModDate (D:20231211201132+03'00') /Keywords (cactoos pdf elegant objects) >>\nendobj",
                    "2 0 obj\n<< /Type /Catalog /Pages 3 0 R >>\nendobj",
                    "3 0 obj\n<< /Type /Pages /Kids [4 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 5 0 R /Contents 6 0 R /Parent 3 0 R /Rotate 90 >>\nendobj",
                    "5 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "6 0 obj\n<< /Length 40 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(Hello World!) Tj\nET\nendstream\nendobj",
                    "trailer << /Root 2 0 R /Size 7 /Info 1 0 R >>",
                    "%%EOF"
                )
            )
        ).affirm();
    }

    @Test
    void buildTwoPagesDocument() throws Exception {
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        new Assertion<>(
            "Must represent a two pages PDF document",
            new TextOf(
                new Document(
                    new Information(
                        "Title", "Hello World",
                        "Subject", "PDF document",
                        "Author", "Fabricio Cabral",
                        "Creator", "cactoos-pdf",
                        "Producer", "cactoos-pdf",
                        "CreationDate", date.asString(),
                        "ModDate", date.asString(),
                        "Keywords", "cactoos pdf elegant objects"
                    ),
                    new Catalog(
                        new DefaultPages(
                            PageFormat.A4,
                            new DefaultPage(
                                new Resources(
                                    new Font(
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        18,
                                        0,
                                        0,
                                        new TextOf("Hello")
                                    )
                                )
                            ),
                            new DefaultPage(
                                new Resources(
                                    new Font(
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        18,
                                        0,
                                        0,
                                        new TextOf("World")
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
                    "1 0 obj\n<< /Title (Hello World) /Subject (PDF document) /Author (Fabricio Cabral) /Creator (cactoos-pdf) /Producer (cactoos-pdf) /CreationDate (D:20231211201132+03'00') /ModDate (D:20231211201132+03'00') /Keywords (cactoos pdf elegant objects) >>\nendobj",
                    "2 0 obj\n<< /Type /Catalog /Pages 3 0 R >>\nendobj",
                    "3 0 obj\n<< /Type /Pages /Kids [4 0 R 7 0 R] /Count 2 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 5 0 R /Contents 6 0 R /Parent 3 0 R >>\nendobj",
                    "5 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "6 0 obj\n<< /Length 33 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(Hello) Tj\nET\nendstream\nendobj",
                    "7 0 obj\n<< /Type /Page /Resources 8 0 R /Contents 9 0 R /Parent 3 0 R >>\nendobj",
                    "8 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "9 0 obj\n<< /Length 33 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(World) Tj\nET\nendstream\nendobj",
                    "trailer << /Root 2 0 R /Size 10 /Info 1 0 R >>",
                    "%%EOF"
                )
            )
        ).affirm();
    }
}
