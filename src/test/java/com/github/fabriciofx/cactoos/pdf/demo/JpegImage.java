/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.demo;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Jpeg;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;

/**
 * JpegImage.
 *
 * @since 0.0.1
 * @checkstyle HideUtilityClassConstructorCheck (200 lines)
 */
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.UnnecessaryLocalRule"})
public final class JpegImage {
    /**
     * Main method.
     *
     * @param args Arguments.
     * @throws Exception if fails
     */
    public static void main(final String[] args) throws Exception {
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
