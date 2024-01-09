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
@SuppressWarnings({"PMD.LoggerIsNotStaticFinal", "PMD.MoreThanOneLogger"})
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
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Increment: was %d become %d from %s",
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
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Value: %d from %s",
                    num,
                    this.source
                )
            ).asString()
        );
        return num;
    }
}
