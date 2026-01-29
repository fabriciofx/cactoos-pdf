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
import org.cactoos.text.TextOf;

/**
 * HelloWorld.
 *
 * @since 0.0.1
 * @checkstyle HideUtilityClassConstructorCheck (200 lines)
 */
@SuppressWarnings("PMD.UseUtilityClass")
public final class HelloWorld {
    /**
     * Main method.
     *
     * @param args Arguments.
     * @throws Exception if fails
     */
    public static void main(final String[] args) throws Exception {
        final Id id = new Serial();
        Files.write(
            new File("hello-world.pdf").toPath(),
            new Document(
                id,
                new DefaultPages(
                    id,
                    new DefaultPage(
                        id,
                        new Contents(
                            new Text(
                                id,
                                new TimesRoman(id, 18),
                                0,
                                500,
                                80,
                                new TextOf("Hello World!")
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
