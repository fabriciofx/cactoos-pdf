/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link FlateEncode}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class FlateEncodeTest {
    @Test
    void encode() throws Exception {
        final Id id = new Serial();
        final Content content = new Text(
            id,
            new TimesRoman(id, 18),
            0,
            0,
            new TextOf("Hello World!")
        );
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        final Deflater deflater = new Deflater();
        try (
            DeflaterOutputStream out = new DeflaterOutputStream(
                expected,
                deflater
            )
        ) {
            out.write(content.asStream());
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new FlateEncode(content).asStream());
        deflater.end();
        new Assertion<>(
            "Must encode a content using Flate algorithm",
            baos.toByteArray(),
            new IsEqual<>(expected.toByteArray())
        ).affirm();
    }
}
