/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.content.Image;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.image.format.Png;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link XObject}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class XObjectTest {
    @Test
    void xobjectDictionary() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must print XObject dictionary",
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
            ).indirect().dictionary(),
            new IsText("<< /XObject << /I3 1 0 R >> >>")
        ).affirm();
    }

    @Test
    void xobjectAsBytes() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must print XObject as bytes",
            new TextOf(
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
                ).indirect().asBytes()
            ),
            new IsText(
                "5 0 obj\n<< /XObject << /I3 1 0 R >> >>\nendobj\n"
            )
        ).affirm();
    }
}
