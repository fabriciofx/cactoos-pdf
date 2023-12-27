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

import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import java.util.Arrays;
import org.cactoos.Bytes;

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
    private final byte[] content;

    /**
     * Ctor.
     *
     * @param dictionary Dictionary
     * @param content Content
     */
    public Definition(
        final Dictionary dictionary,
        final byte[] content
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
        final byte[] content
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
        final byte[] content
    ) {
        this.id = id;
        this.generation = generation;
        this.dict = dictionary;
        this.content = Arrays.copyOf(content, content.length);
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
        return Arrays.copyOf(this.content, this.content.length);
    }
}
