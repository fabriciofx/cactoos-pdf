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
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.Format;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import com.github.fabriciofx.cactoos.pdf.text.Date;
import org.cactoos.text.Concatenated;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link Information}.
 *
 * @since 0.0.1
 */
final class InformationTest {
    @Test
    void info() throws Exception {
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        new Assertion<>(
            "Must contain metadata contents",
            new TextOf(
                new Information(
                    new Serial(),
                    "Title", "Hello World",
                    "Subject", "PDF document",
                    "Author", "Fabricio Cabral",
                    "Creator", "cactoos-pdf",
                    "Producer", "cactoos-pdf",
                    "CreationDate", date.asString(),
                    "ModDate", date.asString(),
                    "Keywords", "cactoos pdf elegant objects"
                ).indirect()
            ),
            new IsText(
                new FormattedText(
                    new Joined(
                        " ",
                        "1 0 obj\n<<",
                        "/Title (Hello World)",
                        "/Subject (PDF document)",
                        "/Author (Fabricio Cabral)",
                        "/Creator (cactoos-pdf)",
                        "/Producer (cactoos-pdf)",
                        "/CreationDate (%s)",
                        "/ModDate (%s)",
                        "/Keywords (cactoos pdf elegant objects)",
                        ">>\nendobj\n"
                    ),
                    date.asString(),
                    date.asString()
                )
            )
        ).affirm();
    }

    @Test
    void empty() throws Exception {
        new Assertion<>(
            "Must contain correct metadata from empty information",
            new TextOf(
                new Information(
                    new Serial()
                ).indirect()
            ),
            new IsText(
                new FormattedText(
                    new Joined(
                        " ",
                        "1 0 obj\n<<",
                        "/Producer (cactoos-pdf)",
                        ">>\nendobj\n"
                    )
                )
            )
        ).affirm();
    }

    @Test
    void document() throws Exception {
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 18);
        new Assertion<>(
            "Must match with information from a hello world PDF document",
            new TextOf(
                new Document(
                    id,
                    new Information(
                        id,
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
                        id,
                        new DefaultPages(
                            id,
                            Format.A4,
                            new DefaultPage(
                                id,
                                new Resources(id, font),
                                new Contents(
                                    new Text(
                                        id,
                                        font,
                                        0,
                                        500,
                                        80,
                                        new TextOf("Hello World!")
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            new StartsWith(
                new Concatenated(
                    "%PDF-1.3\n%���������\n",
                    "2 0 obj\n<< /Title (Hello World) /Subject (PDF document) ",
                    "/Author (Fabricio Cabral) /Creator (cactoos-pdf) ",
                    "/Producer (cactoos-pdf) ",
                    "/CreationDate (D:20231211201132+03'00') ",
                    "/ModDate (D:20231211201132+03'00') ",
                    "/Keywords (cactoos pdf elegant objects) >>\nendobj"
                )
            )
        ).affirm();
    }
}
