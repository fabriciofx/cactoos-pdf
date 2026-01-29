/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.text;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;

/**
 * Date.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
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
     * @param second Second
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
            Locale.ENGLISH,
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
