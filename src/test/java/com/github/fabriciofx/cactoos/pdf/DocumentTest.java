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
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.PngImage;
import com.github.fabriciofx.cactoos.pdf.count.ObjectCount;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Font;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;
import com.github.fabriciofx.cactoos.pdf.resource.ProcSet;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.text.Joined;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Document}.
 *
 * @since 0.0.1
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.ExcessiveMethodLength"
    }
)
final class DocumentTest {
//    @Test
//    void buildDocument() throws Exception {
//        final org.cactoos.Text content = new Joined(
//            " ",
//            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
//            "dolore eu irure incididunt. Velit officia occaecat est",
//            "adipisicing mollit veniam. Minim sunt est culpa labore.",
//            "Ut culpa et nulla sunt labore aliqua ipsum laborum nostrud sit",
//            "deserunt officia labore. Sunt laboris id labore sit ex. Eiusmod",
//            "nulla eu incididunt excepteur minim officia dolore veniam",
//            "labore enim quis reprehenderit. Magna in laboris irure enim non",
//            "deserunt laborum mollit labore id amet."
//        );
//        final Count count = new ObjectCount();
//        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
//        final String filename = "src/test/resources/document/HelloWorld.pdf";
//        final byte[] expected = Files.readAllBytes(
//            new File(filename).toPath()
//        );
//        final byte[] actual = new Document(
//            count,
//            new Information(
//                count,
//                new MapOf<>(
//                    new MapEntry<>("Title", "Hello World"),
//                    new MapEntry<>("Subject", "PDF document"),
//                    new MapEntry<>("Author", "Fabricio Cabral"),
//                    new MapEntry<>("Creator", "cactoos-pdf"),
//                    new MapEntry<>("Producer", "cactoos-pdf"),
//                    new MapEntry<>("CreationDate", date.asString()),
//                    new MapEntry<>("ModDate", date.asString()),
//                    new MapEntry<>("Keywords", "cactoos pdf elegant objects")
//                )
//            ),
//            new Catalog(
//                count,
//                new DefaultPages(
//                    count,
//                    PageFormat.A4,
//                    new DefaultPage(
//                        count,
//                        new Resources(
//                            new Font(
//                                count,
//                                new FontFamily("Times-Roman", "Type1"),
//                                "F1"
//                            )
//                        ),
//                        new Contents(
//                            new FlateEncode(
//                                new Text(count, 18, 0, 500, 80, 20, content)
//                            )
//                        )
//                    ),
//                    new Rotate(
//                        new DefaultPage(
//                            count,
//                            new Resources(
//                                new Font(
//                                    count,
//                                    new FontFamily("Times-Roman", "Type1"),
//                                    "F1"
//                                )
//                            ),
//                            new Contents(
//                                new FlateEncode(
//                                    new Text(count, 18, 0, 500, 80, 20, content)
//                                )
//                            )
//                        ),
//                        90
//                    ),
//                    new DefaultPage(
//                        count,
//                        new Resources(
//                            new Font(
//                                count,
//                                new FontFamily("Times-Roman", "Type1"),
//                                "F1"
//                            )
//                        ),
//                        new Contents(
//                            new FlateEncode(
//                                new Text(count, 18, 0, 500, 80, 20, content)
//                            )
//                        )
//                    )
//                )
//            )
//        ).asBytes();
//        new Assertion<>(
//            "Must match with HelloWorld PDF document",
//            expected,
//            new IsEqual<>(actual)
//        ).affirm();
//    }
//
//    @Disabled
//    @Test
//    void buildFile() throws Exception {
//        final org.cactoos.Text content = new Joined(
//            " ",
//            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
//            "dolore eu irure incididunt. Velit officia occaecat est",
//            "adipisicing mollit veniam. Minim sunt est culpa labore.",
//            "Ut culpa et nulla sunt labore aliqua ipsum laborum nostrud sit",
//            "deserunt officia labore. Sunt laboris id labore sit ex. Eiusmod",
//            "nulla eu incididunt excepteur minim officia dolore veniam",
//            "labore enim quis reprehenderit. Magna in laboris irure enim non",
//            "deserunt laborum mollit labore id amet."
//        );
//        final File file = new File("HelloWorld.pdf");
//        final Count count = new ObjectCount();
//        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
//        Files.write(
//            file.toPath(),
//            new Document(
//                count,
//                new Information(
//                    count,
//                    new MapOf<>(
//                        new MapEntry<>("Title", "Hello World"),
//                        new MapEntry<>("Subject", "PDF document"),
//                        new MapEntry<>("Author", "Fabricio Cabral"),
//                        new MapEntry<>("Creator", "cactoos-pdf"),
//                        new MapEntry<>("Producer", "cactoos-pdf"),
//                        new MapEntry<>("CreationDate", date.asString()),
//                        new MapEntry<>("ModDate", date.asString()),
//                        new MapEntry<>(
//                            "Keywords",
//                            "cactoos pdf elegant objects"
//                        )
//                    )
//                ),
//                new Catalog(
//                    count,
//                    new DefaultPages(
//                        count,
//                        PageFormat.A4,
//                        new DefaultPage(
//                            count,
//                            new Resources(
//                                new Font(
//                                    count,
//                                    new FontFamily("Times-Roman", "Type1"),
//                                    "F1"
//                                )
//                            ),
//                            new Contents(
//                                new FlateEncode(
//                                    new Text(count, 18, 0, 500, 80, 20, content)
//                                )
//                            )
//                        ),
//                        new Rotate(
//                            new DefaultPage(
//                                count,
//                                new Resources(
//                                    new Font(
//                                        count,
//                                        new FontFamily("Times-Roman", "Type1"),
//                                        "F1"
//                                    )
//                                ),
//                                new Contents(
//                                    new FlateEncode(
//                                        new Text(
//                                            count,
//                                            18,
//                                            0,
//                                            500,
//                                            80,
//                                            20,
//                                            content
//                                        )
//                                    )
//                                )
//                            ),
//                            90
//                        ),
//                        new DefaultPage(
//                            count,
//                            new Resources(
//                                new Font(
//                                    count,
//                                    new FontFamily("Times-Roman", "Type1"),
//                                    "F1"
//                                )
//                            ),
//                            new Contents(
//                                new FlateEncode(
//                                    new Text(count, 18, 0, 500, 80, 20, content)
//                                )
//                            )
//                        )
//                    )
//                )
//            ).asBytes()
//        );
//    }

    @Disabled
    @Test
    void buildFileWithImage() throws Exception {
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
        final File file = new File("HelloWorld.pdf");
        final Count count = new ObjectCount();
        final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
        final PngImage png = new PngImage(
            count,
            "src/test/resources/image/logo.png"
        );
        final Image image = new Image(
            count,
            "I1",
            png
        );
        Files.write(
            file.toPath(),
            new Document(
                count,
                new Information(
                    count,
                    new MapOf<>(
                        new MapEntry<>("Title", "Hello World")
                    )
                ),
                new Catalog(
                    count,
                    new DefaultPages(
                        count,
                        PageFormat.A4,
                        new DefaultPage(
                            count,
                            new Resources(
                                count,
                                new ProcSet(),
                                new Font(
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                ),
                                new XObject(image.name(), png)
                            ),
                            new Contents(image)
                        )
                    )
                )
            ).asBytes()
        );
    }
}
