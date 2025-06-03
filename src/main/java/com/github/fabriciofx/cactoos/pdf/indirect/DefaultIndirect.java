/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.indirect;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import org.cactoos.text.FormattedText;

/**
 * DefaultIndirect.
 *
 * @since 0.0.1
 */
public final class DefaultIndirect implements Indirect {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Dictionary.
     */
    private final Dictionary dict;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param dictionary Dictionary
     */
    public DefaultIndirect(
        final int number,
        final int generation,
        final Dictionary dictionary
    ) {
        this.number = number;
        this.generation = generation;
        this.dict = dictionary;
    }

    @Override
    public Reference reference() {
        return new Reference(this.number, this.generation);
    }

    @Override
    public Dictionary dictionary() {
        return this.dict;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(
            new FormattedText(
                "%d %d obj\n",
                Locale.ENGLISH,
                this.number,
                this.generation
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        output.write(this.dict.asBytes());
        output.write("\nendobj\n".getBytes(StandardCharsets.UTF_8));
        return output.toByteArray();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof DefaultIndirect
            && DefaultIndirect.class.cast(obj).number == this.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number, this.generation, this.dict);
    }
}
