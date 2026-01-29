/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Jpeg;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Image}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
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
}
