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
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Catalog;
import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.Courier;
import com.github.fabriciofx.cactoos.pdf.resource.font.FontEnvelope;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.Symbol;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import com.github.fabriciofx.cactoos.pdf.resource.font.ZapfDingbats;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Concatenated;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link FontEnvelope}.
 *
 * @since 0.0.1
 */
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
    void fontAsBytes() throws Exception {
        new Assertion<>(
            "Must must build Times-Roman font as bytes",
            new TextOf(
                new TimesRoman(new Serial(), 12).indirect().asBytes()
            ),
            new IsText(
                new Concatenated(
                    "1 0 obj\n<< /Type /Font /BaseFont /Times-Roman ",
                    "/Subtype /Type1 >>\nendobj\n"
                )
            )
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
            new Catalog(
                id,
                new DefaultPages(
                    id,
                    PageFormat.A4,
                    new DefaultPage(
                        id,
                        new Resources(
                            id,
                            times,
                            helvetica,
                            courier,
                            symbol,
                            zapf
                        ),
                        new Contents(
                            new Text(
                                id,
                                times,
                                10,
                                100,
                                80,
                                text
                            ),
                            new Text(
                                id,
                                helvetica,
                                10,
                                200,
                                80,
                                text
                            ),
                            new Text(
                                id,
                                courier,
                                10,
                                300,
                                80,
                                text
                            ),
                            new Text(
                                id,
                                symbol,
                                10,
                                400,
                                80,
                                text
                            ),
                            new Text(
                                id,
                                zapf,
                                10,
                                500,
                                80,
                                text
                            )
                        )
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

    @Disabled
    @Test
    void buildFileWithFonts() throws Exception {
        final File file = new File("fonts.pdf");
        final Id id = new Serial();
        final Font times = new TimesRoman(id, 16);
        final Font helvetica = new Helvetica(id, 16);
        final Font courier = new Courier(id, 16);
        final Font symbol = new Symbol(id, 16);
        final Font zapf = new ZapfDingbats(id, 16);
        final org.cactoos.Text text = new TextOf(
            "The quick brown fox jumps over the lazy dog"
        );
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
                                times,
                                helvetica,
                                courier,
                                symbol,
                                zapf
                            ),
                            new Contents(
                                new Text(
                                    id,
                                    times,
                                    10,
                                    100,
                                    80,
                                    text
                                ),
                                new Text(
                                    id,
                                    helvetica,
                                    10,
                                    200,
                                    80,
                                    text
                                ),
                                new Text(
                                    id,
                                    courier,
                                    10,
                                    300,
                                    80,
                                    text
                                ),
                                new Text(
                                    id,
                                    symbol,
                                    10,
                                    400,
                                    80,
                                    text
                                ),
                                new Text(
                                    id,
                                    zapf,
                                    10,
                                    500,
                                    80,
                                    text
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
