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

import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Font;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Document}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class DocumentTest {
    @Test
    void buildDocument() {
        final Count count = new ObjectCount();
        new Assertion<>(
            "Must represent a PDF document",
            new TextOf(
                new Document(
                    count,
                    new Information(count, "Hello World"),
                    new Catalog(
                        count,
                        new DefaultPages(
                            count,
                            PageFormat.A4,
                            new DefaultPage(
                                count,
                                new Resources(
                                    new Font(
                                        count,
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        count,
                                        18,
                                        0,
                                        0,
                                        new TextOf(
                                            "Hello World with (, ), \\ and \r"
                                        )
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
                    "1 0 obj\n<< /Title (Hello World) >>\nendobj",
                    "6 0 obj\n<< /Type /Catalog /Pages 5 0 R >>\nendobj",
                    "5 0 obj\n<< /Type /Pages /Kids [4 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 2 0 R /Contents 3 0 R /Parent 5 0 R >>\nendobj",
                    "2 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "3 0 obj\n<< /Length 62 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(Hello World with \\(, \\), \\\\ and \\r) Tj\nET\nendstream\nendobj",
                    "trailer << /Root 6 0 R /Size 7 >>",
                    "%%%%EOF"
                )
            )
        ).affirm();
    }

    @Test
    void buildMultiTextDocument() {
        final Count count = new ObjectCount();
        new Assertion<>(
            "Must represent a PDF document",
            new TextOf(
                new Document(
                    count,
                    new Information(count, "Hello World"),
                    new Catalog(
                        count,
                        new DefaultPages(
                            count,
                            PageFormat.A4,
                            new DefaultPage(
                                count,
                                new Resources(
                                    new Font(
                                        count,
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        count,
                                        18,
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
                    "1 0 obj\n<< /Title (Hello World) >>\nendobj",
                    "6 0 obj\n<< /Type /Catalog /Pages 5 0 R >>\nendobj",
                    "5 0 obj\n<< /Type /Pages /Kids [4 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 2 0 R /Contents 3 0 R /Parent 5 0 R >>\nendobj",
                    "2 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "3 0 obj",
                    "<< /Length 567 >>",
                    "stream",
                    "BT /F1 18 Tf 0 0 Td 20 TL",
                    "(Lorem ea et aliquip culpa aute amet elit nostrud) Tj T*",
                    "(culpa veniam dolore eu irure incididunt. Velit) Tj T*",
                    "(officia occaecat est adipisicing mollit veniam.) Tj T*",
                    "(Minim sunt est culpa labore. Ut culpa et nulla) Tj T*",
                    "(sunt labore aliqua ipsum laborum nostrud sit) Tj T*",
                    "(deserunt officia labore. Sunt laboris id labore) Tj T*",
                    "(sit ex. Eiusmod nulla eu incididunt excepteur) Tj T*",
                    "(minim officia dolore veniam labore enim quis) Tj T*",
                    "(reprehenderit. Magna in laboris irure enim non) Tj T*",
                    "(deserunt laborum mollit labore id amet.) Tj",
                    "ET",
                    "endstream",
                    "endobj",
                    "trailer << /Root 6 0 R /Size 7 >>",
                    "%%%%EOF"
                )
            )
        ).affirm();
    }

    @Test
    void buildTwoPagesDocument() {
        final Count count = new ObjectCount();
        new Assertion<>(
            "Must represent a two pages PDF document",
            new TextOf(
                new Document(
                    count,
                    new Information(count, "Hello World"),
                    new Catalog(
                        count,
                        new DefaultPages(
                            count,
                            PageFormat.A4,
                            new DefaultPage(
                                count,
                                new Resources(
                                    new Font(
                                        count,
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        count,
                                        18,
                                        0,
                                        0,
                                        new TextOf("Hello")
                                    )
                                )
                            ),
                            new DefaultPage(
                                count,
                                new Resources(
                                    new Font(
                                        count,
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        count,
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
                    "1 0 obj\n<< /Title (Hello World) >>\nendobj",
                    "9 0 obj\n<< /Type /Catalog /Pages 8 0 R >>\nendobj",
                    "8 0 obj\n<< /Type /Pages /Kids [4 0 R 7 0 R] /Count 2 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 2 0 R /Contents 3 0 R /Parent 8 0 R >>\nendobj",
                    "2 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "3 0 obj\n<< /Length 33 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(Hello) Tj\nET\nendstream\nendobj",
                    "7 0 obj\n<< /Type /Page /Resources 5 0 R /Contents 6 0 R /Parent 8 0 R >>\nendobj",
                    "5 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "6 0 obj\n<< /Length 33 >>\nstream\nBT /F1 18 Tf 0 0 Td\n(World) Tj\nET\nendstream\nendobj",
                    "trailer << /Root 9 0 R /Size 10 >>",
                    "%%%%EOF"
                )
            )
        ).affirm();
    }

    @Disabled
    @Test
    void buildFile() throws Exception {
        final File file = new File("HelloWorld.pdf");
        final Count count = new ObjectCount();
        Files.write(
            file.toPath(),
            new Document(
                count,
                new Information(count, "Hello World"),
                new Catalog(
                    count,
                    new DefaultPages(
                        count,
                        PageFormat.A4,
                        new DefaultPage(
                            count,
                            new Resources(
                                new Font(
                                    count,
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new Text(
                                    count,
                                    18,
                                    0,
                                    0,
                                    new TextOf("Hello")
                                )
                            )
                        ),
                        new DefaultPage(
                            count,
                            new Resources(
                                new Font(
                                    count,
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new Text(
                                    count,
                                    18,
                                    0,
                                    0,
                                    new TextOf("World")
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }

    @Disabled
    @Test
    void buildMultiFile() throws Exception {
        final File file = new File("HelloWorld.pdf");
        final Count count = new ObjectCount();
        Files.write(
            file.toPath(),
            new Document(
                count,
                new Information(count, "Hello World"),
                new Catalog(
                    count,
                    new DefaultPages(
                        count,
                        PageFormat.A4,
                        new DefaultPage(
                            count,
                            new Resources(
                                new Font(
                                    count,
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new Text(
                                    count,
                                    18,
                                    0,
                                    500,
                                    80,
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
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
