/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.text.TextOf;

/**
 * Break a Text according a limit.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.0.1
 */
public final class Multiline extends IterableEnvelope<Text> {
    /**
     * New line character.
     */
    private static final String NEW_LINE = "\n";

    /**
     * Word separator.
     */
    private static final String SPACE = " ";

    /**
     * Split regular expression.
     */
    private static final String SPLIT_REGEX = "\\s+";

    /**
     * Default max limit.
     */
    private static final int MAX_LIMIT = 80;

    /**
     * Ctor.
     *
     * <p> By default, the limit is 80 characters.
     *
     * @param text A CharSequence
     */
    public Multiline(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * <p> By default, the limit is 80 characters.
     *
     * @param text The Text
     */
    public Multiline(final Text text) {
        this(text, Multiline.MAX_LIMIT);
    }

    /**
     * Ctor.
     *
     * @param text A CharSequence
     * @param limit Limit of the result string
     */
    public Multiline(final CharSequence text, final int limit) {
        this(new TextOf(text), limit);
    }

    /**
     * Ctor.
     *
     * @param text The Text
     * @param limit Limit of the result string
     */
    public Multiline(final Text text, final int limit) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    () -> {
                        final String[] words = text.asString().split(
                            Multiline.SPLIT_REGEX
                        );
                        final StringBuilder lines = new StringBuilder();
                        final StringBuilder word = new StringBuilder(0);
                        int length = 0;
                        for (int idx = 0; idx < words.length; ++idx) {
                            word.setLength(0);
                            word.append(words[idx]);
                            final int size = Multiline.SPACE.length()
                                + word.length();
                            if (length + size > limit) {
                                if (idx > 0) {
                                    lines.append(Multiline.NEW_LINE);
                                }
                                length = 0;
                            }
                            if (
                                idx < words.length - 1
                                    && length + size + words[idx + 1].length()
                                        <= limit
                            ) {
                                word.append(Multiline.SPACE);
                            }
                            lines.append(word);
                            length = length + word.length();
                        }
                        return new IteratorOf<>(
                            lines.toString().split(Multiline.NEW_LINE)
                        );
                    }
                )
            )
        );
    }
}
