/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Document;
import com.github.fabriciofx.cactoos.pdf.Font;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.page.DefaultPage;
import com.github.fabriciofx.cactoos.pdf.pages.DefaultPages;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
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
        final Id id = new Serial();
        final Font font = new TimesRoman(id, 12);
        final byte[] actual = new Document(
            id,
            new DefaultPages(
                id,
                new DefaultPage(
                    id,
                    new Contents(
                        new Justify(
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
            )
        ).asBytes();
        new Assertion<>(
            "Must match with justified PDF document",
            new BytesOf(new ResourceOf("document/justify.pdf")).asBytes(),
            new IsEqual<>(actual)
        ).affirm();
    }
}
