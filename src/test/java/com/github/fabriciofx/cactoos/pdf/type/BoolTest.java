/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link Bool}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class BoolTest {
    @Test
    void value() throws Exception {
        new Assertion<>(
            "Must represent a boolean object",
            !new Bool(false).value(),
            new IsTrue()
        ).affirm();
    }
}
