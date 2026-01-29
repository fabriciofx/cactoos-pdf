/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link ProcSet}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class ProcSetTest {
    @Test
    void procSetDictionary() throws Exception {
        new Assertion<>(
            "Must print ProcSet dictionary",
            new ProcSet().indirect().dictionary(),
            new IsText("<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI] >>")
        ).affirm();
    }

    @Test
    void procSetAsBytes() throws Exception {
        new Assertion<>(
            "Must no print ProcSet bytes",
            new ProcSet().indirect().asBytes(),
            new IsEqual<>(new byte[0])
        ).affirm();
    }
}
