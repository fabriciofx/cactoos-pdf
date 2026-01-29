/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.demo;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.TextOf;

/**
 * TwoFonts.
 *
 * @since 0.0.1
 * @checkstyle HideUtilityClassConstructorCheck (200 lines)
 */
@SuppressWarnings({"PMD.UseUtilityClass", "PMD.UnnecessaryLocalRule"})
public final class TwoFonts {
    /**
     * Main method.
     *
     * @param args Arguments.
     * @throws Exception if fails
     */
    public static void main(final String[] args) throws Exception {
        final Id id = new Serial();
        final Font times = new TimesRoman(id, 18);
        final Font helvetica = new Helvetica(id, 18);
        final File file = new File("two-fonts.pdf");
        Files.write(
            file.toPath(),
            new Document(
                id,
                new DefaultPages(
                    id,
                    new DefaultPage(
                        id,
                        new Contents(
                            new Text(
                                id,
                                times,
                                20,
                                500,
                                80,
                                new TextOf("Hello World!")
                            ),
                            new Text(
                                id,
                                helvetica,
                                20,
                                600,
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
