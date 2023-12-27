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
package com.github.fabriciofx.cactoos.pdf.text;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;

/**
 * Date.
 *
 * @since 0.0.1
 */
public final class Date implements Text {
    /**
     * ZonedDateTime.
     */
    private final ZonedDateTime datetime;

    /**
     * Ctor.
     */
    public Date() {
        this(ZonedDateTime.now());
    }

    /**
     * Ctor.
     *
     * @param year Year
     * @param month Month
     * @param day Day
     * @param hour Hour
     * @param minute Minute
     * @param second Seconde
     * @param zone Zone ID
     * @checkstyle ParameterNumberCheck (15 lines)
     */
    public Date(
        final int year,
        final int month,
        final int day,
        final int hour,
        final int minute,
        final int second,
        final String zone
    ) {
        this(
            ZonedDateTime.of(
                LocalDateTime.of(year, month, day, hour, minute, second),
                ZoneId.of(zone)
            )
        );
    }

    /**
     * Ctor.
     *
     * @param datetime A ZonedDateTime
     */
    public Date(final ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String asString() throws Exception {
        final String[] offset = this.datetime.getOffset().toString().split(":");
        final String hours = offset[0];
        final String minutes = offset[1];
        return new FormattedText(
            "D:%s%s'%s'",
            this.datetime.format(
                DateTimeFormatter.ofPattern(
                    "uuuuMMddHHmmss"
                )
            ),
            hours,
            minutes
        ).asString();
    }
}
