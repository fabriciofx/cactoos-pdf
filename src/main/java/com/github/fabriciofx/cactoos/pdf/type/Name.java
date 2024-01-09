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
package com.github.fabriciofx.cactoos.pdf.type;

import com.github.fabriciofx.cactoos.pdf.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.cactoos.text.FormattedText;

/**
 * Name.
 *
 * @since 0.0.1
 */
public final class Name implements Type<String> {
    /**
     * Value.
     */
    private final String text;

    /**
     * Ctor.
     *
     * @param text The name
     */
    public Name(final String text) {
        this.text = text;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String value() throws Exception {
        return this.text;
    }

    @Override
    public String asString() throws Exception {
        return new FormattedText("/%s", Locale.ENGLISH, this.text).asString();
    }

    @Override
    public boolean equals(final Object name) {
        return name instanceof Name
            && Name.class.cast(name).text.equals(this.text);
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }
}
