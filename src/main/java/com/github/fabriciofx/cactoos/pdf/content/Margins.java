/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
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
 * Margins.
 *
 * @since 0.0.1
 */
@SuppressWarnings(
    {
        "PMD.UnusedPrivateField",
        "PMD.SingularField",
        "PMD.UseUnderscoresInNumericLiterals",
        "PMD.StringInstantiation"
    }
)
public final class Margins implements Content {
    /**
     * One centimeter in points.
     *
     * ONE_CM = 72 (points in 1 inch) / 2.54 (1 inch in cm)
     */
    private static final double ONE_CM = 28.346_456_7;

    /**
     * Top margin.
     */
    private final double top;

    /**
     * Right margin.
     */
    private final double right;

    /**
     * Bottom margin.
     */
    private final double bottom;

    /**
     * Left margin.
     */
    private final double left;

    /**
     * Page Format.
     */
    private final Format format;

    /**
     * Text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param text Text to be decorated
     */
    public Margins(final Text text) {
        this(1.0, 1.0, 1.0, 1.0, Format.A4, text);
    }

    /**
     * Ctor.
     *
     * @param top Top margin
     * @param right Right margin
     * @param bottom Bottom margin
     * @param left Left margin
     * @param format Page format
     * @param text Text to be decorated
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Margins(
        final double top,
        final double right,
        final double bottom,
        final double left,
        final Format format,
        final Text text
    ) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.format = format;
        this.origin = text;
    }

    @Override
    public byte[] asStream() throws Exception {
        final Pattern pattern = Pattern.compile("BT.*TL");
        final Matcher matcher = pattern.matcher(
            new String(this.origin.asStream())
        );
        final StringBuffer stream = new StringBuffer();
        while (matcher.find()) {
            final String[] parts = matcher.group().split("\\s+");
            final String fontname = parts[1];
            final String fontsize = parts[2];
            final String leading = parts[7];
            matcher.appendReplacement(
                stream,
                new FormattedText(
                    "BT %s %s Tf %.2f %.2f Td %s TL",
                    Locale.ENGLISH,
                    fontname,
                    fontsize,
                    this.left * Margins.ONE_CM,
                    this.format.height() - this.top * Margins.ONE_CM,
                    leading
                ).asString()
            );
        }
        matcher.appendTail(stream);
        return stream.toString().getBytes(StandardCharsets.UTF_8);
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
    public List<Resource> resource() {
        return this.origin.resource();
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
    }
}
