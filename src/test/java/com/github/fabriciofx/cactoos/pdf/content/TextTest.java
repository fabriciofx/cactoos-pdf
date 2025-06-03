/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.id.Serial;
import com.github.fabriciofx.cactoos.pdf.resource.font.TimesRoman;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Text}.
 *
 * @since 0.0.1
 */
final class TextTest {
    @Test
    void oneline() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must don't break a small text",
            new TextOf(
                new Text(
                    id,
                    new TimesRoman(id, 18),
                    0,
                    0,
                    50,
                    new TextOf("Hello World")
                ).indirect().asBytes()
            ),
            new IsText(
                new Joined(
                    "\n",
                    "2 0 obj",
                    "<< /Length 57 >>",
                    "stream",
                    "BT /F1 18 Tf 0.00 0.00 Td 21.60 TL",
                    "(Hello World) Tj T*",
                    "ET",
                    "endstream",
                    "endobj\n"
                )
            )
        ).affirm();
    }

    @Test
    void multiLines() throws Exception {
        final Id id = new Serial();
        new Assertion<>(
            "Must break a big text into multiline",
            new TextOf(
                new Text(
                    id,
                    new TimesRoman(id, 18),
                    0,
                    0,
                    50,
                    new Joined(
                        " ",
                        "Lorem ea et aliquip culpa aute",
                        "amet elit nostrud culpa veniam",
                        "dolore eu irure incididunt.",
                        "Velit officia occaecat est",
                        "adipisicing mollit veniam.",
                        "Minim sunt est culpa labore.",
                        "Ut culpa et nulla sunt labore",
                        "aliqua ipsum laborum nostrud sit",
                        "deserunt officia labore. Sunt",
                        "laboris id labore sit ex. Eiusmod",
                        "nulla eu incididunt excepteur",
                        "minim officia dolore veniam",
                        "labore enim quis reprehenderit.",
                        "Magna in laboris irure enim non",
                        "deserunt laborum mollit labore",
                        "id amet."
                    )
                ).indirect().asBytes()
            ),
            new IsText(
                new Joined(
                    "\n",
                    "2 0 obj",
                    "<< /Length 579 >>",
                    "stream",
                    "BT /F1 18 Tf 0.00 0.00 Td 21.60 TL",
                    "(Lorem ea et aliquip culpa aute amet elit nostrud) Tj T*",
                    "(culpa veniam dolore eu irure incididunt. Velit) Tj T*",
                    "(officia occaecat est adipisicing mollit veniam.) Tj T*",
                    "(Minim sunt est culpa labore. Ut culpa et nulla) Tj T*",
                    "(sunt labore aliqua ipsum laborum nostrud sit) Tj T*",
                    "(deserunt officia labore. Sunt laboris id labore) Tj T*",
                    "(sit ex. Eiusmod nulla eu incididunt excepteur) Tj T*",
                    "(minim officia dolore veniam labore enim quis) Tj T*",
                    "(reprehenderit. Magna in laboris irure enim non) Tj T*",
                    "(deserunt laborum mollit labore id amet.) Tj T*",
                    "ET",
                    "endstream",
                    "endobj\n"
                )
            )
        ).affirm();
    }
}
