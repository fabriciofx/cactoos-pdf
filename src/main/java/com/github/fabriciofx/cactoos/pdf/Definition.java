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

import com.github.fabriciofx.cactoos.pdf.text.Indirect;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * Definition.
 *
 * @since 0.0.1
 */
public final class Definition implements Bytes {
    /**
     * Object id.
     */
    private final int id;

    /**
     * Object generation.
     */
    private final int generation;

    /**
     * Dictionary.
     */
    private final Dictionary dict;

    /**
     * Content.
     */
    private final List<Bytes> content;

    /**
     * Ctor.
     *
     * @param dictionary Dictionary
     * @param content Content
     */
    public Definition(
        final Dictionary dictionary,
        final Bytes... content
    ) {
        this(-1, 0, dictionary, content);
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param dictionary Dictionary
     * @param content Content
     */
    public Definition(
        final int id,
        final Dictionary dictionary,
        final Bytes... content
    ) {
        this(id, 0, dictionary, content);
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param dictionary Dictionary
     * @param content Content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Definition(
        final int id,
        final int generation,
        final Dictionary dictionary,
        final Bytes... content
    ) {
        this(id, generation, dictionary, new ListOf<>(content));
    }

    /**
     * Ctor.
     *
     * @param id Object id
     * @param generation Object generation
     * @param dictionary Dictionary
     * @param content Content
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Definition(
        final int id,
        final int generation,
        final Dictionary dictionary,
        final List<Bytes> content
    ) {
        this.id = id;
        this.generation = generation;
        this.dict = dictionary;
        this.content = content;
    }

    /**
     * Create an object reference.
     *
     * @return A reference
     */
    public Reference reference() {
        return new Reference(this.id, this.generation);
    }

    /**
     * Create a dictionary.
     *
     * @return A dictionary
     */
    public Dictionary dictionary() {
        return this.dict;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!this.dict.isEmpty()) {
            if (this.id > 0) {
                baos.write(new Indirect(this.id, 0).asBytes());
                baos.write(this.dict.asBytes());
                baos.write("\nendobj\n".getBytes(StandardCharsets.UTF_8));
            } else {
                baos.write(this.dict.asBytes());
            }
        }
        for (final Bytes bytes : this.content) {
            baos.write(bytes.asBytes());
        }
        return baos.toByteArray();
    }
}
