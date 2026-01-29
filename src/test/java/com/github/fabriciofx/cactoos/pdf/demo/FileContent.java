/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.demo;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;

/**
 * FileContent.
 *
 * @since 0.0.1
 * @checkstyle HideUtilityClassConstructorCheck (200 lines)
 */
@SuppressWarnings("PMD.UseUtilityClass")
public final class FileContent {
    /**
     * Main method.
     *
     * @param args Arguments.
     * @throws Exception if fails
     */
    public static void main(final String[] args) throws Exception {
        final Id id = new Serial();
        Files.write(
            new File("text-20k.pdf").toPath(),
            new Document(
                id,
                new DefaultPages(
                    id,
                    new DefaultPage(
                        id,
                        new Contents(
                            new Text(
                                id,
                                new TimesRoman(id, 12),
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
            ).asBytes()
        );
    }
}
