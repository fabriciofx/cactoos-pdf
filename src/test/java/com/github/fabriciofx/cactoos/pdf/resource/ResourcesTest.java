/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Concatenated;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Resources}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class ResourcesTest {
    @Test
    void resourcesDictionary() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must represent a resources dictionary",
            new Resources(
                id,
                new ProcSet(),
                new TimesRoman(id, 12),
                new XObject(
                    id,
                    new Image(
                        id,
                        new Png(
                            id,
                            new BytesOf(new ResourceOf("image/logo.png"))
                        ),
                        28,
                        766
                    )
                )
            ).indirect().dictionary(),
            new IsText(
                new Concatenated(
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    " /Font << /F1 1 0 R >> /XObject << /I4 2 0 R >> >>"
                )
            )
        ).affirm();
    }

    @Test
    void resourcesAsBytes() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must represent a resources as bytes",
            new TextOf(
                new Resources(
                    id,
                    new ProcSet(),
                    new TimesRoman(id, 12),
                    new XObject(
                        id,
                        new Image(
                            id,
                            new Png(
                                id,
                                new BytesOf(new ResourceOf("image/logo.png"))
                            ),
                            28,
                            766
                        )
                    )
                ).indirect().asBytes()
            ),
            new IsText(
                "7 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 1 0 R >> /XObject << /I4 2 0 R >> >>\nendobj\n"
            )
        ).affirm();
    }

    @Test
    void resourcesDictionaryWithTwoFonts() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must represent a resources dictionary",
            new Resources(
                id,
                new ProcSet(),
                new TimesRoman(id, 12),
                new Helvetica(id, 12)
            ).indirect().dictionary(),
            new IsText(
                new Concatenated(
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    " /Font << /F1 1 0 R /F2 2 0 R >> >>"
                )
            )
        ).affirm();
    }

    @Test
    void documentWithTwoFonts() throws Exception {
        final Id id = new Serial();
        final Font times = new TimesRoman(id, 18);
        final Font helvetica = new Helvetica(id, 18);
        final byte[] actual = new Document(
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
        ).asBytes();
        new Assertion<>(
            "Must match with hello world PDF document using two fonts",
            new BytesOf(new ResourceOf("document/two-fonts.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }
}
