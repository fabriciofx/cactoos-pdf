/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;

/**
 * Dictionary.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
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
            Locale.ENGLISH,
            values.toString()
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (!this.entries.isEmpty()) {
            output.write(this.asString().getBytes(StandardCharsets.UTF_8));
        }
        if (!this.stream.isEmpty()) {
            output.write(this.stream.get(0).asBytes());
        }
        return output.toByteArray();
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
     * @throws Exception if fails
     */
    public Dictionary merge(final Dictionary dictionary) throws Exception {
        final Map<Name, Type<?>> elements = new LinkedHashMap<>(this.entries);
        for (final Name name : dictionary.entries.keySet()) {
            if (elements.containsKey(name)) {
                final Dictionary origin = dictionary.get(name.value());
                final Dictionary target = (Dictionary) elements.get(name);
                target.entries.putAll(origin.entries);
            } else {
                elements.putAll(dictionary.entries);
            }
        }
        Dictionary result = new Dictionary(elements);
        if (!dictionary.stream.isEmpty()) {
            result = result.with(dictionary.stream.get(0));
        }
        return result;
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
