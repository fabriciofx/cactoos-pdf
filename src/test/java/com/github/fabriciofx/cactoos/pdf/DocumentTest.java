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
import com.github.fabriciofx.cactoos.pdf.content.FlateEncode;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.page.Rotate;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Font;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.ProcSet;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.Joined;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Document}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength"})
final class DocumentTest {
    @Test
    void buildDocument() throws Exception {
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
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final String filename = "src/test/resources/document/HelloWorld.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        final byte[] actual = new Document(
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
                            new FlateEncode(
                                new Text(18, 0, 500, 80, 20, content)
                            )
                        )
                    ),
                    new Rotate(
                        new DefaultPage(
                            new Resources(
                                new Font(
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new FlateEncode(
                                    new Text(18, 0, 500, 80, 20, content)
                                )
                            )
                        ),
                        90
                    ),
                    new DefaultPage(
                        new Resources(
                            new Font(
                                new FontFamily("Times-Roman", "Type1"),
                                "F1"
                            )
                        ),
                        new Contents(
                            new FlateEncode(
                                new Text(18, 0, 500, 80, 20, content)
                            )
                        )
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with HelloWorld PDF document",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentWithPngImage() throws Exception {
        final String filename = "src/test/resources/document/image-png.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        final Image image = new Image(
            "I1",
            new Png(
                "src/test/resources/image/logo.png"
            )
        );
        final byte[] actual = new Document(
            new Information(
                "Title", "Hello World"
            ),
            new Catalog(
                new DefaultPages(
                    PageFormat.A4,
                    new DefaultPage(
                        new Resources(
                            new ProcSet(),
                            new Font(
                                new FontFamily("Times-Roman", "Type1"),
                                "F1"
                            ),
                            new XObject(image.name(), image.content())
                        ),
                        new Contents(
                            image
                        )
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with PDF document with a PNG image",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileWithPngImage() throws Exception {
        final Image image = new Image(
            "I1",
            new Png(
                "src/test/resources/image/logo.png"
            )
        );
        final File file = new File("image-png.pdf");
        Files.write(
            file.toPath(),
            new Document(
                new Information(
                    "Title", "Hello World"
                ),
                new Catalog(
                    new DefaultPages(
                        PageFormat.A4,
                        new DefaultPage(
                            new Resources(
                                new ProcSet(),
                                new Font(
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                ),
                                new XObject(image.name(), image.content())
                            ),
                            new Contents(
                                image
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }

    @Disabled
    @Test
    void buildFile() throws Exception {
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
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final File file = new File("HelloWorld.pdf");
        Files.write(
            file.toPath(),
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
                                new FlateEncode(
                                    new Text(18, 0, 500, 80, 20, content)
                                )
                            )
                        ),
                        new Rotate(
                            new DefaultPage(
                                new Resources(
                                    new Font(
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new FlateEncode(
                                        new Text(18, 0, 500, 80, 20, content)
                                    )
                                )
                            ),
                        90
                        ),
                        new DefaultPage(
                            new Resources(
                                new Font(
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new FlateEncode(
                                    new Text(18, 0, 500, 80, 20, content)
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
