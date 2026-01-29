/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.resource;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.NoReferenceIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.util.List;

/**
 * ProcSet.
 *
 * @since 0.0.1
 */
public final class ProcSet implements Resource {
    @Override
    public Indirect indirect(final int... parent) throws Exception {
        return new NoReferenceIndirect(
            new Dictionary()
                .add(
                    "ProcSet",
                    new Array("PDF", "Text", "ImageB", "ImageC", "ImageI")
                )
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        // Empty of purpose.
    }
}
