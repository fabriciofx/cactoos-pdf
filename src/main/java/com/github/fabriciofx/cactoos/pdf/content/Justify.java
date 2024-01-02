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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Definition;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.page.PageFormat;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cactoos.text.FormattedText;

/**
 * Justify.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.StringInstantiation")
public final class Justify implements Content {
    /**
     * Text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param text Text to be justified
     */
    public Justify(final Text text) {
        this.origin = text;
    }

    @Override
    public byte[] asStream() throws Exception {
        final String str = new String(this.origin.asStream());
        Pattern pattern = Pattern.compile("(?<=\\().*?(?=\\))");
        Matcher matcher = pattern.matcher(str);
        final StringBuilder lines = new StringBuilder();
        while (matcher.find()) {
            lines.append(matcher.group()).append(' ');
        }
        pattern = Pattern.compile("BT.*TL");
        matcher = pattern.matcher(str);
        final StringBuilder stream = new StringBuilder();
        while (matcher.find()) {
            stream.append(matcher.group()).append('\n');
        }
        final double scale = 72.0 / 25.4;
        final double rmargin = 28.35 / scale;
        final double cmargin = rmargin / 10.0;
        final double width = PageFormat.A4.width() / scale - 2 * rmargin;
        final double fontsize = this.origin.font().size() / scale;
        final double wmax = (width - 2 * cmargin) * 1000 / fontsize;
        int idx = 0;
        int spaces = 0;
        int linespace = 0;
        int length = 0;
        int start = 0;
        final String line = lines.toString();
        while (idx < line.length()) {
            final char chr = line.charAt(idx);
            if (chr == ' ') {
                linespace = length;
                ++spaces;
            }
            length = length + this.origin.font().width(chr);
            if (length > wmax) {
                while (line.charAt(idx) != ' ') {
                    --idx;
                }
                ++idx;
                double wordspace = 0;
                if (spaces > 1) {
                    wordspace =
                        (wmax - linespace) / 1000 * fontsize / (spaces - 1);
                }
                stream.append(
                    new FormattedText(
                        "%.3f Tw\n", wordspace * scale
                    ).asString()
                )
                    .append('(')
                    .append(line.substring(start, idx - 1))
                    .append(") Tj T*\n");
                length = 0;
                linespace = 0;
                spaces = 0;
                start = idx;
            }
            ++idx;
        }
        stream.append("ET\n");
        return stream.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Definition definition(final Id id) throws Exception {
        final Definition definition = this.origin.definition(id);
        final byte[] stream = this.asStream();
        return new Definition(
            definition.reference().id(),
            definition.reference().generation(),
            definition.dictionary()
                .add("Length", new Int(stream.length))
                .with(new Stream(stream))
        );
    }
}
