/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.id;

import com.github.fabriciofx.cactoos.pdf.Id;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged.
 *
 * Log for {@link Serial}
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class Logged implements Id {
    /**
     * The Id.
     */
    private final Id origin;

    /**
     * Where data comes from.
     */
    private final String source;

    /**
     * Logger.
     */
    private final Logger logger;

    /**
     * Logger level.
     */
    private final Unchecked<Level> level;

    /**
     * Ctor.
     *
     * @param id Id to be logged
     * @param source The name of source data
     */
    public Logged(final Id id, final String source) {
        this(id, source, Logger.getLogger(source));
    }

    /**
     * Ctor.
     *
     * @param id Id to be logged
     * @param source The name of source data
     * @param logger Message logger
     */
    public Logged(final Id id, final String source, final Logger logger) {
        this.origin = id;
        this.source = source;
        this.logger = logger;
        this.level = new Unchecked<>(
            new Sticky<>(
                () -> {
                    Level lvl = logger.getLevel();
                    if (lvl == null) {
                        Logger parent = logger;
                        while (lvl == null) {
                            parent = parent.getParent();
                            lvl = parent.getLevel();
                        }
                    }
                    return lvl;
                }
            )
        );
    }

    @Override
    public int increment() {
        final int num = this.origin.increment();
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        final String method = stack[2].getMethodName();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "%s called increment(): was %d become %d from %s",
                    method,
                    num,
                    num + 1,
                    this.source
                )
            ).asString()
        );
        return num;
    }

    @Override
    public Integer value() throws Exception {
        final int num = this.origin.value();
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        final String method = stack[2].getMethodName();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "%s called value(): %d from %s",
                    method,
                    num,
                    this.source
                )
            ).asString()
        );
        return num;
    }
}
