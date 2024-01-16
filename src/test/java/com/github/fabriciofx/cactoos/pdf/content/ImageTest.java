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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Jpeg;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Image}.
 *
 * @since 0.0.1
 */
final class ImageTest {
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
            new DefaultPages(
                id,
                new DefaultPage(
                    id,
                    new Contents(
                        image
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
                new DefaultPages(
                    id,
                    new DefaultPage(
                        id,
                        new Contents(
                            image
                        )
                    )
                )
            ).asBytes()
        );
    }

    @Test
    void buildDocumentWithJpegImage() throws Exception {
        final Id id = new Serial();
        final Image image = new Image(
            id,
            new Jpeg(
                id,
                new BytesOf(new ResourceOf("image/sample-1.jpg"))
            ),
            0,
            100
        );
        final byte[] actual = new Document(
            id,
            new DefaultPages(
                id,
                new DefaultPage(
                    id,
                    new Contents(
                        image
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match with PDF document with a JPEG image",
            new BytesOf(new ResourceOf("document/image-jpg.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileWithJpegImage() throws Exception {
        final Id id = new Serial();
        final Image image = new Image(
            id,
            new Jpeg(
                id,
                new BytesOf(new ResourceOf("image/sample-1.jpg"))
            ),
            0,
            100
        );
        final File file = new File("image-jpg.pdf");
        Files.write(
            file.toPath(),
            new Document(
                id,
                new DefaultPages(
                    id,
                    new DefaultPage(
                        id,
                        new Contents(
                            image
                        )
                    )
                )
            ).asBytes()
        );
    }
}
