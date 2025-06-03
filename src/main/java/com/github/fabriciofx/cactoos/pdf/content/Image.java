/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.image.Format;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.XObject;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Image.
 *
 * @since 0.0.1
 */
public final class Image implements Content {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Resource.
     */
    private final Resource resrc;

    /**
     * Image Format.
     */
    private final Format fmt;

    /**
     * Position X.
     */
    private final double posx;

    /**
     * Position Y.
     */
    private final double posy;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param format Raw image format
     * @param posx Position X
     * @param posy Position Y
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final Id id,
        final Format format,
        final double posx,
        final double posy
    ) {
        this(
            id.increment(),
            0,
            id,
            format,
            posx,
            posy
        );
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param id Id number
     * @param format Raw image format
     * @param posx Position X
     * @param posy Position Y
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final int number,
        final int generation,
        final Id id,
        final Format format,
        final double posx,
        final double posy
    ) {
        this.number = number;
        this.generation = generation;
        this.fmt = format;
        this.posx = posx;
        this.posy = posy;
        this.resrc = new XObject(id, this);
    }

    /**
     * Image name.
     *
     * @return The image name
     */
    public String name() {
        return new UncheckedText(
            new FormattedText("I%d", this.number)
        ).asString();
    }

    @Override
    public byte[] asStream() throws Exception {
        return new FormattedText(
            "q %d 0 0 %d %.2f %.2f cm /%s Do Q",
            Locale.ENGLISH,
            this.fmt.width(),
            this.fmt.height(),
            this.posx,
            this.posy,
            this.name()
        ).asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public List<Resource> resource() {
        return new ListOf<>(this.resrc);
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        return new DefaultIndirect(this.number, this.generation, dictionary);
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect());
        this.fmt.print(indirects);
    }

    /**
     * Image format.
     *
     * @return The image format
     */
    public Format format() {
        return this.fmt;
    }
}
