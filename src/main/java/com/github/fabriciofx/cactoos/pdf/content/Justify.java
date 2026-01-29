/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.page.Format;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cactoos.text.FormattedText;

/**
 * Justify.
 *
 * @since 0.0.1
 */
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
        final double width = Format.A4.width() / scale - 2 * rmargin;
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
                        "%.3f Tw\n",
                        Locale.ENGLISH,
                        wordspace * scale
                    ).asString()
                )
                    .append('(')
                    .append(line, start, idx - 1)
                    .append(") Tj T*\n");
                length = 0;
                linespace = 0;
                spaces = 0;
                start = idx;
            }
            ++idx;
        }
        stream.append("ET");
        return stream.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public List<Resource> resource() {
        return this.origin.resource();
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final Indirect indirect = this.origin.indirect();
        final byte[] stream = this.asStream();
        return new DefaultIndirect(
            indirect.reference().number(),
            indirect.reference().generation(),
            indirect.dictionary()
                .add("Length", new Int(stream.length))
                .with(new Stream(stream))
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
    }
}
