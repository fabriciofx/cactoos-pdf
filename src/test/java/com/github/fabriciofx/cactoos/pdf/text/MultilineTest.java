/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Multiline}.
 * @since 0.0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class MultilineTest {
    @Test
    void multilineAnEmptyText() {
        final String content = "";
        new Assertion<>(
            "Must multiline an empty text",
            new Multiline(content, 8),
            new IsEqual<>(new IterableOf<>(new TextOf(content)))
        ).affirm();
    }

    @Test
    void multilineText() {
        final Text content = new Joined(
            " ",
            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
            "dolore eu irure incididunt. Velit officia occaecat est",
            "adipisicing mollit veniam. Minim sunt est culpa labore."
        );
        new Assertion<>(
            "Must multiline a text at limit 50 characters",
            new Multiline(content, 50),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("Lorem ea et aliquip culpa aute amet elit nostrud"),
                    new TextOf("culpa veniam dolore eu irure incididunt. Velit"),
                    new TextOf("officia occaecat est adipisicing mollit veniam."),
                    new TextOf("Minim sunt est culpa labore.")
                )
            )
        ).affirm();
    }

    @Test
    void multilineTextOneCharSmaller() {
        final String msg = "Hello World!";
        new Assertion<>(
            "Must multiline a text with same length",
            new Multiline(msg, 5),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello"), new TextOf("World!")))
        ).affirm();
    }

    @Test
    void multilineTextWithLimitBiggerThanLength() {
        final String msg = "cactoos framework";
        new Assertion<>(
            "Must multiline a text with limit bigger than length",
            new Multiline(msg, 50),
            new IsEqual<>(new IterableOf<>(new TextOf("cactoos framework")))
        ).affirm();
    }

    @Test
    void multilineTextBiggerThanDefaultLimit() {
        new Assertion<>(
            "Must abbreviate a text bigger than default max width",
            new Multiline(
                new Joined(
                    " ",
                    "The quick brown fox jumps over the lazy black dog",
                    "and after that returned to the cave"
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new Joined(
                        " ",
                        "The quick brown fox jumps over the lazy",
                        "black dog and after that returned to "
                    ),
                    new TextOf("the cave")
                )
            )
        ).affirm();
    }

    @Test
    void multilineTextWithNewLines() {
        final Text content = new Joined(
            "\n",
            "Lorem ea et aliquip culpa aute amet elit ",
            "nostrud culpa veniam dolore eu irure ",
            "incididunt. Velit officia occaecat est ",
            "adipisicing mollit veniam. Minim sunt ",
            "est culpa labore."
        );
        new Assertion<>(
            "Must multiline a text at limit 50 characters",
            new Multiline(content, 50),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("Lorem ea et aliquip culpa aute amet elit nostrud"),
                    new TextOf("culpa veniam dolore eu irure incididunt. Velit"),
                    new TextOf("officia occaecat est adipisicing mollit veniam."),
                    new TextOf("Minim sunt est culpa labore.")
                )
            )
        ).affirm();
    }
}
