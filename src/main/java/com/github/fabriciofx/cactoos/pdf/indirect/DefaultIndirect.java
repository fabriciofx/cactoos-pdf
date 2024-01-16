/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023-2024 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.pdf.indirect;

import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;
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
     * Content.
     */
    private final List<Bytes> contents;

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param dictionary Dictionary
     * @param contents Contents
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultIndirect(
        final int number,
        final int generation,
        final Dictionary dictionary,
        final Bytes... contents
    ) {
        this(number, generation, dictionary, new ListOf<>(contents));
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param dictionary Dictionary
     * @param contents Contents
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultIndirect(
        final int number,
        final int generation,
        final Dictionary dictionary,
        final List<Bytes> contents
    ) {
        this.number = number;
        this.generation = generation;
        this.dict = dictionary;
        this.contents = contents;
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                Locale.ENGLISH,
                this.number,
                this.generation
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        baos.write(this.dict.asBytes());
        baos.write("\nendobj\n".getBytes(StandardCharsets.UTF_8));
        for (final Bytes bytes : this.contents) {
            baos.write(bytes.asBytes());
        }
        return baos.toByteArray();
    }
}
