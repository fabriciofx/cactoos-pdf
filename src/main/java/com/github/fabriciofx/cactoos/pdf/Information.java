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
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Document Information Dictionary.
 *
 * @since 0.0.1
 */
public final class Information implements Object, Bytes {
    /**
     * Object number.
     */
    private final int number;

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
     * @param count Counter
     * @param metadata Metadata
     */
    public Information(
        final Count count, final Map<String, String> metadata
    ) {
        this(count.increment(), 0, metadata);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Object generation
     * @param metadata Metadata
     */
    public Information(
        final int number,
        final int generation,
        final Map<String, String> metadata
    ) {
        this.number = number;
        this.generation = generation;
        this.metadata = metadata;
    }

    @Override
    public String reference() {
        return new UncheckedText(
            new FormattedText(
                "%d %d R",
                this.number,
                this.generation
            )
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        final StringBuilder params = new StringBuilder(128);
        params.append(this.number)
            .append(' ')
            .append(this.generation)
            .append(" obj\n<<");
        addIfContains(params, this.metadata, "Title");
        addIfContains(params, this.metadata, "Subject");
        addIfContains(params, this.metadata, "Author");
        addIfContains(params, this.metadata, "Creator");
        addIfContains(params, this.metadata, "Producer");
        addIfContains(params, this.metadata, "CreationDate");
        addIfContains(params, this.metadata, "ModDate");
        addIfContains(params, this.metadata, "Keywords");
        params.append(" >>\nendobj\n");
        return params.toString().getBytes();
    }

    /**
     * Add a metadata to builder if metadata contains data.
     *
     * @param builder A StringBuilder
     * @param metadata The metadata map
     * @param data The data
     */
    private static void addIfContains(
        final StringBuilder builder,
        final Map<String, String> metadata,
        final String data
    ) {
        if (metadata.containsKey(data)) {
            builder.append(" /")
                .append(data).append(" (")
                .append(metadata.get(data))
                .append(')');
        }
    }
}
