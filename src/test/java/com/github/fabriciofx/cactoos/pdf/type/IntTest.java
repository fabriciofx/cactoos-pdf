/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;

/**
 * Test case for {@link Int}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class IntTest {
    @Test
    void value() throws Exception {
        new Assertion<>(
            "Must represent an integer object",
            new Int(123).value(),
            new IsNumber(123)
        ).affirm();
    }
}
