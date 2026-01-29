/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Format}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class FormatTest {
    @Test
    void size() {
        new Assertion<>(
            "Must print correct A4 page's width and height",
            Format.A4,
            new IsText("595.28 841.89")
        ).affirm();
    }
}
