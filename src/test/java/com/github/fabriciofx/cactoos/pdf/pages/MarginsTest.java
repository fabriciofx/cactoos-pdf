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
package com.github.fabriciofx.cactoos.pdf.pages;

import com.github.fabriciofx.cactoos.pdf.Catalog;
import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Information;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.resource.Font;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import java.io.File;
import java.nio.file.Files;
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
        final Font font = new Font(
            new FontFamily("Times-Roman", "Type1"),
            "F1"
        );
        final byte[] actual = new Document(
            new Information(
                "Title", "Hello World"
            ),
            new Catalog(
                new Margins(
                    2.5,
                    2.5,
                    2.5,
                    2.5,
                    new DefaultPages(
                        PageFormat.A4,
                        new DefaultPage(
                            new Resources(font),
                            new Contents(
                                new Text(font, 12, 0, 500, 60, 14, content)
                            )
                        )
                    )
                )
            )
        ).asBytes();
        final String filename = "src/test/resources/document/margins.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        new Assertion<>(
            "Must match with margins PDF document",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileWithMargins() throws Exception {
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
        final Font font = new Font(
            new FontFamily("Times-Roman", "Type1"),
            "F1"
        );
        final File file = new File("margins.pdf");
        Files.write(
            file.toPath(),
            new Document(
                new Information(
                    "Title", "Hello World"
                ),
                new Catalog(
                    new Margins(
                        2.5,
                        2.5,
                        2.5,
                        2.5,
                        new DefaultPages(
                            PageFormat.A4,
                            new DefaultPage(
                                new Resources(font),
                                new Contents(
                                    new Text(font, 12, 0, 500, 60, 14, content)
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
