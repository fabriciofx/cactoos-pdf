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
package com.github.fabriciofx.cactoos.pdf;

import java.util.Map;
import org.cactoos.Bytes;

/**
 * Document Information Dictionary.
 *
 * @since 0.0.1
 */
public final class Information implements Object, Bytes {
    /**
     * Object id.
     */
    private final int id;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Metadata.
     */
    private final Map<String, String> metadata;

    /**
     * Ctor.
     *
     * @param id Object id
     * @param metadata Metadata
     */
    public Information(
        final Id id, final Map<String, String> metadata
    ) {
        this(id.increment(), 0, metadata);
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param metadata Metadata
     */
    public Information(
        final int id,
        final int generation,
        final Map<String, String> metadata
    ) {
        this.id = id;
        this.generation = generation;
        this.metadata = metadata;
    }

    @Override
    public Reference reference() {
        return new Reference(this.id, this.generation);
    }

    @Override
    public byte[] asBytes() throws Exception {
        final StringBuilder data = new StringBuilder(128);
        data.append(this.id)
            .append(' ')
            .append(this.generation)
            .append(" obj\n<<");
        addIfContains(data, this.metadata, "Title");
        addIfContains(data, this.metadata, "Subject");
        addIfContains(data, this.metadata, "Author");
        addIfContains(data, this.metadata, "Creator");
        addIfContains(data, this.metadata, "Producer");
        addIfContains(data, this.metadata, "CreationDate");
        addIfContains(data, this.metadata, "ModDate");
        addIfContains(data, this.metadata, "Keywords");
        data.append(" >>\nendobj\n");
        return data.toString().getBytes();
    }

    /**
     * Add a metadata to data if metadata contains name.
     *
     * @param data A data builder
     * @param metadata The metadata
     * @param name The metadata name
     */
    private static void addIfContains(
        final StringBuilder data,
        final Map<String, String> metadata,
        final String name
    ) {
        if (metadata.containsKey(name)) {
            data.append(" /")
                .append(name)
                .append(" (")
                .append(metadata.get(name))
                .append(')');
        }
    }
}
