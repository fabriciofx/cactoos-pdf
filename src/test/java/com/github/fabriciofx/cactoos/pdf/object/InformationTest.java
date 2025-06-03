/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.object;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
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
                    new DefaultPages(
                        id,
                        new DefaultPage(
                            id,
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
