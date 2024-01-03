/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2023 Fabrício Barros Cabral
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
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Array.
 *
 * @since 0.0.1
 */
public final class Array implements Type<Type<?>> {
    /**
     * Values.
     */
    private final List<Type<?>> values;

    /**
     * Ctor.
     *
     * @param names Shortcut for Names
     */
    public Array(final String... names) {
        this(Arrays.stream(names).map(Name::new).collect(Collectors.toList()));
    }

    /**
     * Ctor.
     *
     * @param values Values
     */
    public Array(final Type<?>... values) {
        this(new ListOf<>(values));
    }

    /**
     * Ctor.
     *
     * @param values Values
     */
    public Array(final List<Type<?>> values) {
        this.values = values;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (final Type<?> type : this.values) {
            baos.write(type.asBytes());
        }
        return baos.toByteArray();
    }

    @Override
    public Type<?> value() throws Exception {
        return this;
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText(
            "[%s]",
            Locale.ENGLISH,
            this.values.stream().map(
                type -> new UncheckedText(type).asString()
            ).collect(Collectors.joining(" "))
        ).asString();
    }

    /**
     * Add an value to array.
     *
     * @param value The value to be added
     * @return A new array with added value
     */
    public Array add(final Type<?> value) {
        final List<Type<?>> tmp = new ListOf<>(this.values);
        tmp.add(value);
        return new Array(tmp);
    }

    /**
     * Get a value in the array.
     *
     * @param index The nth value
     * @param <T> Type of value to be read
     * @return The value
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final int index) {
        return (T) this.values.get(index);
    }
}
