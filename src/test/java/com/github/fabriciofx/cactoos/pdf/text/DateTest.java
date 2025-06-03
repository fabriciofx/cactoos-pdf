/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Date}.
 *
 * @since 0.0.1
 */
final class DateTest {
    @Test
    void show() throws Exception {
        new Assertion<>(
            "Must represent a PDF date",
            new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3"),
            new IsText("D:20231211201132+03'00'")
        ).affirm();
    }
}
