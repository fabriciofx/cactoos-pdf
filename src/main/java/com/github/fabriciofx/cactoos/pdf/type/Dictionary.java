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
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;

/**
 * Dictionary.
 *
 * @since 0.0.1
 */
public final class Dictionary implements Type<Dictionary> {
    /**
     * Entries.
     */
    private final Map<Name, Type<?>> entries;

    /**
     * Stream, if there is one.
     */
    private final List<Stream> stream;

    /**
     * Ctor.
     */
    public Dictionary() {
        this(new ListOf<>(), new LinkedHashMap<>());
    }

    /**
     * Ctor.
     *
     * @param entries Entries
     */
    public Dictionary(final Map<Name, Type<?>> entries) {
        this(new ListOf<>(), entries);
    }

    /**
     * Ctor.
     *
     * @param stream Stream, if there is one
     * @param entries Entries
     */
    public Dictionary(
        final List<Stream> stream,
        final Map<Name, Type<?>> entries
    ) {
        this.stream = stream;
        this.entries = entries;
    }

    @Override
    public Dictionary value() throws Exception {
        return this;
    }

    @Override
    public String asString() throws Exception {
        final StringBuilder values = new StringBuilder();
        for (final Map.Entry<Name, Type<?>> entry : this.entries.entrySet()) {
            if (entry.getValue().asString().isEmpty()) {
                values.append(entry.getKey().asString());
            } else {
                values.append(entry.getKey().asString())
                    .append(' ')
                    .append(entry.getValue().asString());
            }
            values.append(' ');
        }
        return new FormattedText(
            "<< %s>>",
            values.toString()
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!this.entries.isEmpty()) {
            baos.write(this.asString().getBytes(StandardCharsets.UTF_8));
        }
        if (!this.stream.isEmpty()) {
            baos.write("\nstream\n".getBytes(StandardCharsets.UTF_8));
            baos.write(this.stream.get(0).value());
            baos.write("\nendstream".getBytes(StandardCharsets.UTF_8));
        }
        return baos.toByteArray();
    }

    /**
     * Add an entry into dictionary.
     *
     * @param name The name of this entry
     * @param value The value of this entry
     * @return A new dictionary with the new entry added
     */
    public Dictionary add(final String name, final Type<?> value) {
        final Map<Name, Type<?>> tmp = new LinkedHashMap<>(this.entries);
        tmp.put(new Name(name), value);
        return new Dictionary(tmp);
    }

    /**
     * Add a data stream into dictionary.
     *
     * @param strm A data stream
     * @return A new dictionary with the stream added
     */
    public Dictionary with(final Stream strm) {
        return new Dictionary(new ListOf<>(strm), this.entries);
    }

    /**
     * Merge two dictionary.
     *
     * @param dictionary A dictionary to be merged
     * @return A new dictionary merged
     */
    public Dictionary merge(final Dictionary dictionary) {
        final Map<Name, Type<?>> elements = new LinkedHashMap<>(this.entries);
        elements.putAll(dictionary.entries);
        return new Dictionary(elements);
    }

    /**
     * Checks if there is an entry with the name.
     *
     * @param key The name to be checked
     * @return True if there is, false if not
     */
    public boolean contains(final String key) {
        return this.entries.containsKey(new Name(key));
    }

    /**
     * Get a value which the name is a key.
     *
     * @param key The name
     * @param <T> The type of value returned
     * @return A value
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) this.entries.get(new Name(key));
    }

    /**
     * Check if a dictionary is empty.
     *
     * @return True if a dictionary is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }
}
