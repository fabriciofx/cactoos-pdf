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
