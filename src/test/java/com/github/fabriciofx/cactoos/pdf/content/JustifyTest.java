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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Catalog;
import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.resource.font.FontEnvelope;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Justify}.
 *
 * @since 0.0.1
 */
final class JustifyTest {
    @Disabled
    @Test
    void justifyText() throws Exception {
        final FontEnvelope font = new TimesRoman(12);
        final byte[] actual = new Document(
            new Catalog(
                new DefaultPages(
                    PageFormat.A4,
                    new DefaultPage(
                        new Resources(font),
                        new Contents(
                            new Justify(
                                new Text(
                                    font,
                                    20,
                                    800,
                                    100,
                                    new TextOf(
                                        new File(
                                            "src/test/resources/text/20k_c1.txt"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ).asBytes();
        final String filename = "src/test/resources/document/justify.pdf";
        final byte[] expected = Files.readAllBytes(
            new File(filename).toPath()
        );
        new Assertion<>(
            "Must match with justified PDF document",
            expected,
            new IsEqual<>(actual)
        ).affirm();
    }

    @Disabled
    @Test
    void buildFileJustifyText() throws Exception {
        final FontEnvelope font = new TimesRoman(12);
        final File file = new File("justify.pdf");
        Files.write(
            file.toPath(),
            new Document(
                new Catalog(
                    new DefaultPages(
                        PageFormat.A4,
                        new DefaultPage(
                            new Resources(font),
                            new Contents(
                                new Justify(
                                    new Text(
                                        font,
                                        20,
                                        800,
                                        100,
                                        new TextOf(
                                            new File(
                                                "src/test/resources/text/20k_c1.txt"
                                            )
                                        )
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
