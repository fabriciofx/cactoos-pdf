/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.Courier;
import com.github.fabriciofx.cactoos.pdf.resource.font.FontEnvelope;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.Symbol;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import com.github.fabriciofx.cactoos.pdf.resource.font.ZapfDingbats;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link FontEnvelope}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class FontTest {
    @Test
    void fontDictionary() throws Exception {
        new Assertion<>(
            "Must must print a Times-Roman font dictionary",
            new TextOf(
                new TimesRoman(new Serial(), 12).indirect().dictionary()
            ),
            new IsText("<< /Font << /F1 1 0 R >> >>")
        ).affirm();
    }

    @Test
    void buildDocumentWithFonts() throws Exception {
        final Id id = new Serial();
        final Font times = new TimesRoman(id, 16);
        final Font helvetica = new Helvetica(id, 16);
        final Font courier = new Courier(id, 16);
        final Font symbol = new Symbol(id, 16);
        final Font zapf = new ZapfDingbats(id, 16);
        final org.cactoos.Text text = new TextOf(
            "The quick brown fox jumps over the lazy dog"
        );
        final byte[] actual = new Document(
            id,
            new DefaultPages(
                id,
                new DefaultPage(
                    id,
                    new Contents(
                        new Text(id, times, 10, 100, 80, text),
                        new Text(id, helvetica, 10, 200, 80, text),
                        new Text(id, courier, 10, 300, 80, text),
                        new Text(id, symbol, 10, 400, 80, text),
                        new Text(id, zapf, 10, 500, 80, text)
                    )
                )
            )
        ).asBytes();
        new Assertion<>(
            "Must match a PDF document with several fonts",
            new BytesOf(new ResourceOf("document/fonts.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }
}
