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
package com.github.fabriciofx.cactoos.pdf.object;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Object;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Literal;
import java.util.List;

/**
 * Document Information Dictionary.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ExcessiveParameterList")
public final class Information implements Object {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Metadata.
     */
    private final Dictionary metadata;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @param name4 Metadata name4
     * @param value4 Metadata value4
     * @param name5 Metadata name5
     * @param value5 Metadata value5
     * @param name6 Metadata name6
     * @param value6 Metadata value6
     * @param name7 Metadata name7
     * @param value7 Metadata value7
     * @param name8 Metadata name8
     * @param value8 Metadata value8
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3,
        final String name4,
        final String value4,
        final String name5,
        final String value5,
        final String name6,
        final String value6,
        final String name7,
        final String value7,
        final String name8,
        final String value8
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
                .add(name4, new Literal(value4))
                .add(name5, new Literal(value5))
                .add(name6, new Literal(value6))
                .add(name7, new Literal(value7))
                .add(name8, new Literal(value8))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @param name4 Metadata name4
     * @param value4 Metadata value4
     * @param name5 Metadata name5
     * @param value5 Metadata value5
     * @param name6 Metadata name6
     * @param value6 Metadata value6
     * @param name7 Metadata name7
     * @param value7 Metadata value7
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3,
        final String name4,
        final String value4,
        final String name5,
        final String value5,
        final String name6,
        final String value6,
        final String name7,
        final String value7
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
                .add(name4, new Literal(value4))
                .add(name5, new Literal(value5))
                .add(name6, new Literal(value6))
                .add(name7, new Literal(value7))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @param name4 Metadata name4
     * @param value4 Metadata value4
     * @param name5 Metadata name5
     * @param value5 Metadata value5
     * @param name6 Metadata name6
     * @param value6 Metadata value6
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3,
        final String name4,
        final String value4,
        final String name5,
        final String value5,
        final String name6,
        final String value6
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
                .add(name4, new Literal(value4))
                .add(name5, new Literal(value5))
                .add(name6, new Literal(value6))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @param name4 Metadata name4
     * @param value4 Metadata value4
     * @param name5 Metadata name5
     * @param value5 Metadata value5
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3,
        final String name4,
        final String value4,
        final String name5,
        final String value5
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
                .add(name4, new Literal(value4))
                .add(name5, new Literal(value5))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @param name4 Metadata name4
     * @param value4 Metadata value4
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3,
        final String name4,
        final String value4
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
                .add(name4, new Literal(value4))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @param name3 Metadata name3
     * @param value3 Metadata value3
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2,
        final String name3,
        final String value3
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
                .add(name3, new Literal(value3))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name1 Metadata name1
     * @param value1 Metadata value1
     * @param name2 Metadata name2
     * @param value2 Metadata value2
     * @checkstyle ParameterNumberCheck (30 lines)
     * @checkstyle ParameterNameCheck (30 lines)
     */
    public Information(
        final Id id,
        final String name1,
        final String value1,
        final String name2,
        final String value2
    ) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name1, new Literal(value1))
                .add(name2, new Literal(value2))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param name Metadata name
     * @param value Metadata value
     */
    public Information(final Id id, final String name, final String value) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add(name, new Literal(value))
        );
    }

    /**
     * Ctor.
     *
     * @param id Id number
     */
    public Information(final Id id) {
        this(
            id.increment(),
            0,
            new Dictionary()
                .add("Producer", new Literal("cactoos-pdf"))
        );
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param metadata Metadata
     */
    public Information(
        final int number,
        final int generation,
        final Dictionary metadata
    ) {
        this.number = number;
        this.generation = generation;
        this.metadata = metadata;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        return new DefaultIndirect(this.number, this.generation, this.metadata);
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
    }
}
