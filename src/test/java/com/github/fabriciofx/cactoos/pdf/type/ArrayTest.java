/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import org.cactoos.text.Joined;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Array}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class ArrayTest {
    @Test
    void array() {
        new Assertion<>(
            "Must represent an array",
            new Array(
                new Int(549),
                new Real(3.14),
                new Bool(false),
                new Literal("Ralph"),
                new Name("SomeName")
            ),
            new IsText("[549 3.14 false (Ralph) /SomeName]")
        ).affirm();
    }

    @Test
    void arrayInArray() {
        new Assertion<>(
            "Must represent an array with an another array inside",
            new Array(
                new Int(549),
                new Real(3.14),
                new Bool(false),
                new Literal("Ralph"),
                new Name("SomeName"),
                new Array(
                    new Int(945),
                    new Real(6.28),
                    new Bool(true),
                    new Literal("Jeff"),
                    new Name("OtherName")
                )
            ),
            new IsText(
                new Joined(
                    " ",
                    "[549 3.14 false (Ralph) /SomeName",
                    "[945 6.28 true (Jeff) /OtherName]]"
                )
            )
        ).affirm();
    }

    @Test
    void addElementToArray() {
        new Assertion<>(
            "Must add a new element to array",
            new Array(
                new Int(549),
                new Real(3.14),
                new Bool(false),
                new Literal("Ralph"),
                new Name("SomeName")
            ).add(new Name("NewName")),
            new IsText("[549 3.14 false (Ralph) /SomeName /NewName]")
        ).affirm();
    }

    @Test
    void getElementFromArray() throws Exception {
        final Int obj = new Array(
            new Int(549),
            new Real(3.14),
            new Bool(false),
            new Literal("Ralph"),
            new Name("SomeName")
        ).get(0);
        new Assertion<>(
            "Must get an element from array",
            obj.value(),
            new IsNumber(549)
        ).affirm();
    }
}
