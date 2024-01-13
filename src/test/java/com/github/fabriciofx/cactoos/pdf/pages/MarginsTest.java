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
package com.github.fabriciofx.cactoos.pdf.pages;

import com.github.fabriciofx.cactoos.pdf.Catalog;
import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Information;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.Format;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Joined;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Margins}.
 *
 * @since 0.0.1
 */
final class MarginsTest {
    @Test
    void margins() throws Exception {
        final Id id = new Serial();
        final org.cactoos.Text content = new Joined(
            " ",
            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
            "dolore eu irure incididunt. Velit officia occaecat est",
            "adipisicing mollit veniam. Minim sunt est culpa labore.",
            "Ut culpa et nulla sunt labore aliqua ipsum laborum nostrud sit",
            "deserunt officia labore. Sunt laboris id labore sit ex. Eiusmod",
            "nulla eu incididunt excepteur minim officia dolore veniam",
            "labore enim quis reprehenderit. Magna in laboris irure enim non",
            "deserunt laborum mollit labore id amet."
        );
        final Font font = new TimesRoman(id, 12);
        final byte[] actual = new Document(
            id,
            new Information(
                id,
                "Title", "Hello World"
            ),
            new Catalog(
                id,
                new Margins(
                    2.5,
                    2.5,
                    2.5,
                    2.5,
                    new DefaultPages(
                        id,
                        Format.A4,
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new Text(id, font, 0, 500, 60, 14, content)
                            )
                        )
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with margins PDF document",
            new BytesOf(new ResourceOf("document/margins.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileWithMargins() throws Exception {
        final Id id = new Serial();
        final org.cactoos.Text content = new Joined(
            " ",
            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
            "dolore eu irure incididunt. Velit officia occaecat est",
            "adipisicing mollit veniam. Minim sunt est culpa labore.",
            "Ut culpa et nulla sunt labore aliqua ipsum laborum nostrud sit",
            "deserunt officia labore. Sunt laboris id labore sit ex. Eiusmod",
            "nulla eu incididunt excepteur minim officia dolore veniam",
            "labore enim quis reprehenderit. Magna in laboris irure enim non",
            "deserunt laborum mollit labore id amet."
        );
        final Font font = new TimesRoman(id, 12);
        final File file = new File("margins.pdf");
        Files.write(
            file.toPath(),
            new Document(
                id,
                new Information(
                    id,
                    "Title", "Hello World"
                ),
                new Catalog(
                    id,
                    new Margins(
                        2.5,
                        2.5,
                        2.5,
                        2.5,
                        new DefaultPages(
                            id,
                            Format.A4,
                            new DefaultPage(
                                id,
                                new Resources(id, font),
                                new Contents(
                                    new Text(id, font, 0, 500, 60, 14, content)
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
