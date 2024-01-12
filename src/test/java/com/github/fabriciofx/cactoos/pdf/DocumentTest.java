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
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Png;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.ProcSet;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
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
            new BytesOf(new ResourceOf("document/hello-world.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentWithPngImage() throws Exception {
        final Id id = new Serial();
        final Image image = new Image(
            id,
            new Png(
                id,
                new BytesOf(new ResourceOf("image/logo.png"))
            ),
            28,
            766
        );
        final byte[] actual = new Document(
            id,
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
            new BytesOf(new ResourceOf("document/image-png.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentWithFileContent() throws Exception {
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
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
                                20,
                                800,
                                100,
                                new TextOf(
                                    new ResourceOf("text/20k_c1.txt")
                                )
                            )
                        )
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with PDF document with a text file",
            new BytesOf(new ResourceOf("document/text-20k.pdf")).asBytes(),
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
                new BytesOf(new ResourceOf("image/logo.png"))
            ),
            28,
            766
        );
        final File file = new File("image-png.pdf");
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
    void buildFileWithFileContent() throws Exception {
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        final File file = new File("text-20k.pdf");
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
                                        new ResourceOf("text/20k_c1.txt")
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
