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
package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;

/**
 * Image.
 *
 * @since 0.0.1
 */
public final class Image implements Content {
    /**
     * Image name.
     */
    private final String label;

    /**
     * Image PNG.
     */
    private final Png png;

    /**
     * Position X.
     */
    private final double posx;

    /**
     * Position Y.
     */
    private final double posy;

    /**
     * Width.
     */
    private final Scalar<Double> width;

    /**
     * Height.
     */
    private final Scalar<Double> height;

    /**
     * Ctor.
     *
     * @param name Image name
     * @param png Raw PNG
     * @param posx Position X
     * @param posy Position Y
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final String name,
        final Png png,
        final double posx,
        final double posy
    ) {
        this(
            name,
            png,
            posx,
            posy,
            () -> (double) png.width(),
            () -> (double) png.height()
        );
    }

    /**
     * Ctor.
     *
     * @param name Image name
     * @param png Raw PNG
     * @param posx Position X
     * @param posy Position Y
     * @param width Image width
     * @param height Image height
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final String name,
        final Png png,
        final double posx,
        final double posy,
        final double width,
        final double height
    ) {
        this(name, png, posx, posy, () -> width, () -> height);
    }

    /**
     * Ctor.
     *
     * @param name Image name
     * @param png Raw PNG
     * @param posx Position X
     * @param posy Position Y
     * @param width Image width
     * @param height Image height
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public Image(
        final String name,
        final Png png,
        final double posx,
        final double posy,
        final Scalar<Double> width,
        final Scalar<Double> height
    ) {
        this.label = name;
        this.png = png;
        this.posx = posx;
        this.posy = posy;
        this.width = width;
        this.height = height;
    }

    /**
     * Image name.
     *
     * @return The image name
     */
    public String name() {
        return this.label;
    }

    @Override
    public byte[] asStream() throws Exception {
        return new FormattedText(
            "q %.2f 0 0 %.2f %.2f %.2f cm /%s Do Q",
            Locale.ENGLISH,
            this.width.value(),
            this.height.value(),
            this.posx,
            this.posy,
            this.label
        ).asString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Indirect indirect(final Id id) throws Exception {
        final int num = id.value();
        final byte[] stream = this.asStream();
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
        return new DefaultIndirect(num, 0, dictionary);
    }

    /**
     * PNG.
     * @return The PNG
     */
    public Png content() {
        return this.png;
    }
}
