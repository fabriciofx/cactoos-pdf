/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import java.util.List;

/**
 * Rotate envelope.
 *
 * @since 0.0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public abstract class RotateEnvelope implements Page {
    /**
     * The Page.
     */
    private final Page origin;

    /**
     * Angle.
     */
    private final int angle;

    /**
     * Ctor.
     *
     * @param page The Page
     * @param angle The angle
     */
    public RotateEnvelope(final Page page, final int angle) {
        this.origin = page;
        this.angle = angle;
    }

    @Override
    public Resources resources() {
        return this.origin.resources();
    }

    @Override
    public Contents contents() {
        return this.origin.contents();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.origin.indirect(parent[0]);
        final Dictionary dictionary = indirect.dictionary().add(
            "Rotate",
            new Int(this.angle)
        );
        return new DefaultIndirect(
            indirect.reference().number(),
            indirect.reference().generation(),
            dictionary
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect(parent[0]));
        this.resources().print(indirects);
        this.contents().print(indirects);
    }
}
