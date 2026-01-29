/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Escaped}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class EscapedTest {
    @Test
    void escaped() throws Exception {
        new Assertion<>(
            "Must escape special characters",
            new Escaped("\\, (, ), \r"),
            new IsText("\\\\, \\(, \\), \\r")
        ).affirm();
    }
}
