/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.LengthOf;

/**
 * FlateEncode.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class FlateEncode implements Content {
    /**
     * The content.
     */
    private final Content origin;

    /**
     * Ctor.
     *
     * @param content The content
     */
    public FlateEncode(final Content content) {
        this.origin = content;
    }

    @Override
    public byte[] asStream() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Deflater deflater = new Deflater();
        try (
            DeflaterOutputStream out = new DeflaterOutputStream(
                baos,
                deflater
            )
        ) {
            new LengthOf(
                new TeeInput(
                    this.origin.asStream(),
                    new OutputTo(out)
                )
            ).value();
            deflater.end();
        }
        return baos.toByteArray();
    }

    @Override
    public List<Resource> resource() {
        return this.origin.resource();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .add("Filter", new Name("FlateDecode"))
            .with(new Stream(stream));
        final Indirect indirect = this.origin.indirect();
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
        indirects.add(this.indirect());
    }
}
