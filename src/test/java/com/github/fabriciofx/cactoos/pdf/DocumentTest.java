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
import com.github.fabriciofx.cactoos.pdf.content.FlateEncode;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.page.Rotate90;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.ProcSet;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import com.github.fabriciofx.cactoos.pdf.text.Date;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
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
    void buildDocumentHelloWorld() throws Exception {
        final byte[] expected = Files.readAllBytes(
            new File("src/test/resources/document/hello-world.pdf").toPath()
        );
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 18);
        final byte[] actual = new Document(
            id,
            new Catalog(
                id,
                new DefaultPages(
                    id,
                    PageFormat.A4,
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
        ).asBytes();
        new Assertion<>(
            "Must match with hello world PDF document",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentAndRotate() throws Exception {
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
        final Id id = new Serial();
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final Font font = new TimesRoman(id, 18);
        final byte[] actual = new Document(
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
                    PageFormat.A4,
                    new DefaultPage(
                        id,
                        new Resources(id, font),
                        new Contents(
                            new FlateEncode(
                                new Text(id, font, 0, 500, 80, 20, content)
                            )
                        )
                    ),
                    new Rotate90(
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new FlateEncode(
                                    new Text(id, font, 0, 500, 80, 20, content)
                                )
                            )
                        )
                    ),
                    new DefaultPage(
                        id,
                        new Resources(id, font),
                        new Contents(
                            new FlateEncode(
                                new Text(id, font, 0, 500, 80, 20, content)
                            )
                        )
                    )
                )
            )
        ).asBytes();
        final String filename = "src/test/resources/document/rotate.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        new Assertion<>(
            "Must match with text and rotate page PDF document",
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
        final Id id = new Serial();
        final Image image = new Image(
            id,
            new Png(
                id,
                "src/test/resources/image/logo.png"
            ),
            28,
            766
        );
        final byte[] actual = new Document(
            id,
            new Information(
                id,
                "Title", "Hello World"
            ),
            new Catalog(
                id,
                new DefaultPages(
                    id,
                    PageFormat.A4,
                    new DefaultPage(
                        id,
                        new Resources(
                            id,
                            new ProcSet(),
                            new TimesRoman(id, 12),
                            new XObject(id, image)
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

    @Test
    void buildDocumentWithFileContent() throws Exception {
        final String filename = "src/test/resources/document/text-20k.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        final byte[] actual = new Document(
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
                    PageFormat.A4,
                    new DefaultPage(
                        id,
                        new Resources(id, font),
                        new Contents(
                            new Text(
                                id,
                                font,
                                20,
                                800,
                                100,
                                new TextOf(
                                    new File(
                                        "src/test/resources/text/20k_c1.txt"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with PDF document with a text file",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileHelloWorld() throws Exception {
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 18);
        final File file = new File("hello-world.pdf");
        Files.write(
            file.toPath(),
            new Document(
                id,
                new Catalog(
                    id,
                    new DefaultPages(
                        id,
                        PageFormat.A4,
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
            ).asBytes()
        );
    }

    @Disabled
    @Test
    void buildFileWithPngImage() throws Exception {
        final Id id = new Serial();
        final Image image = new Image(
            id,
            new Png(
                id,
                "src/test/resources/image/logo.png"
            ),
            28,
            766
        );
        final File file = new File("image-png.pdf");
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
                    new DefaultPages(
                        id,
                        PageFormat.A4,
                        new DefaultPage(
                            id,
                            new Resources(
                                id,
                                new ProcSet(),
                                new TimesRoman(id, 12),
                                new XObject(id, image)
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
    void buildFileAndRotate() throws Exception {
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
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 18);
        final File file = new File("rotate.pdf");
        Files.write(
            file.toPath(),
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
                        PageFormat.A4,
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new FlateEncode(
                                    new Text(id, font, 0, 500, 80, 20, content)
                                )
                            )
                        ),
                        new Rotate90(
                            new DefaultPage(
                                id,
                                new Resources(id, font),
                                new Contents(
                                    new FlateEncode(
                                        new Text(id, font, 0, 500, 80, 20, content)
                                    )
                                )
                            )
                        ),
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new FlateEncode(
                                    new Text(id, font, 0, 500, 80, 20, content)
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
    void buildFileWithFileContent() throws Exception {
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        final File file = new File("text-20k.pdf");
        Files.write(
            file.toPath(),
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
                        PageFormat.A4,
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new Text(
                                    id,
                                    font,
                                    20,
                                    800,
                                    100,
                                    new TextOf(
                                        new File(
                                            "src/test/resources/text/20k_c1.txt"
                                        )
                                    )
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
    void buildFileWithoutInformation() throws Exception {
        final File file = new File("text-20k-without-info.pdf");
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        Files.write(
            file.toPath(),
            new Document(
                id,
                new Catalog(
                    id,
                    new DefaultPages(
                        id,
                        PageFormat.A4,
                        new DefaultPage(
                            id,
                            new Resources(id, font),
                            new Contents(
                                new Text(
                                    id,
                                    font,
                                    20,
                                    800,
                                    100,
                                    new TextOf(
                                        new File(
                                            "src/test/resources/text/20k_c1.txt"
                                        )
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
