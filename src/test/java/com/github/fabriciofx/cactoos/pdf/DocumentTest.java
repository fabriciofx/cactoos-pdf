/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.content.Text;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Jpeg;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.Courier;
import com.github.fabriciofx.cactoos.pdf.resource.font.Helvetica;
import com.github.fabriciofx.cactoos.pdf.resource.font.Symbol;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import com.github.fabriciofx.cactoos.pdf.resource.font.ZapfDingbats;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.HeadOf;
import org.cactoos.io.InputOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Concatenated;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Document}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class DocumentTest {
    @Test
    void buildDocumentHelloWorld() throws Exception {
        final Id id = new Serial();
        final byte[] actual = new Document(
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
        ).asBytes();
        new Assertion<>(
            "Must match with hello world PDF document",
            new BytesOf(new ResourceOf("document/hello-world.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentWithFileContent() throws Exception {
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        final byte[] actual = new Document(
            id,
            new DefaultPages(
                id,
                new DefaultPage(
                    id,
                    new Contents(
                        new Text(
                            id,
                            font,
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
        ).asBytes();
        new Assertion<>(
            "Must match with PDF document with a text file",
            new BytesOf(new ResourceOf("document/text-20k.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void buildDocumentWithFontsAndImages() throws Exception {
        final Id id = new Serial();
        final Font times = new TimesRoman(id, 16);
        final Font helvetica = new Helvetica(id, 16);
        final Font courier = new Courier(id, 16);
        final Font symbol = new Symbol(id, 16);
        final Font zapf = new ZapfDingbats(id, 16);
        final Image cat = new Image(
            id,
            new Jpeg(
                id,
                new BytesOf(new ResourceOf("image/sample-1.jpg"))
            ),
            0,
            100
        );
        final Image logo = new Image(
            id,
            new Png(
                id,
                new BytesOf(new ResourceOf("image/logo.png"))
            ),
            28,
            766
        );
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
                        cat,
                        logo,
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
            "Must match with PDF document with several fonts and images",
            new BytesOf(new ResourceOf("document/fonts-images.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }

    @Test
    void crossReference() {
        new Assertion<>(
            "Must match",
            new TextOf(
                new HeadOf(
                    new InputOf(
                        new BytesOf(new ResourceOf("document/hello-world.pdf"))
                    ),
                    374
                )
            ),
            new IsText(
                new Concatenated(
                    "%PDF-1.3\n",
                    "%���������\n",
                    "5 0 obj\n<< /Producer (cactoos-pdf) >>\nendobj\n",
                    "6 0 obj\n<< /Type /Catalog /Pages 4 0 R >>\nendobj\n",
                    "4 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj\n",
                    "3 0 obj\n<< /Type /Page /Resources 7 0 R /Contents [2 0 R] /Parent 4 0 R >>\nendobj\n",
                    "7 0 obj\n<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] /Font << /F1 1 0 R >> >>\nendobj\n"
                )
            )
        ).affirm();
    }
}
