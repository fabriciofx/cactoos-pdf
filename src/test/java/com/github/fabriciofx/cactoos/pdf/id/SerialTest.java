/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.id;

import com.github.fabriciofx.cactoos.pdf.Id;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;

/**
 * Test case for {@link Serial}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class SerialTest {
    @Test
    void startInOne() {
        new Assertion<>(
            "Must start in one",
            new Serial().value(),
            new IsNumber(1)
        ).affirm();
    }

    @Test
    void incrementLastValue() {
        new Assertion<>(
            "Must increment return last value",
            new Serial().increment(),
            new IsNumber(1)
        ).affirm();
    }

    @Test
    void incrementAfterValueReturnNewValue() throws Exception {
        final Id id = new Serial();
        id.increment();
        new Assertion<>(
            "Must start in one",
            id.value(),
            new IsNumber(2)
        ).affirm();
    }
}
