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
package com.github.fabriciofx.cactoos.pdf.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.cactoos.Bytes;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Joined;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Dictionary}.
 *
 * @since 0.0.1
 */
final class DictionaryTest {
    @Test
    void dictionary() {
        new Assertion<>(
            "Must represent a dictionary",
            new Dictionary()
                .add("Type", new Name("Example"))
                .add("Subtype", new Name("DictionaryExample"))
                .add("Version", new Real(0.01))
                .add("IntegerItem", new Int(12))
                .add("StringItem", new Literal("a string"))
                .add(
                    "Subdictionary",
                    new Dictionary()
                        .add("Item1", new Real(0.4))
                        .add("Item2", new Bool(true))
                        .add("LastItem", new Literal("not !"))
                        .add("VeryLastItem", new Literal("OK"))
                ),
            new IsText(
                new Joined(
                    " ",
                    "<< /Type /Example /Subtype /DictionaryExample",
                    "/Version 0.01 /IntegerItem 12 /StringItem (a string)",
                    "/Subdictionary << /Item1 0.4 /Item2 true",
                    "/LastItem (not !) /VeryLastItem (OK) >> >>"
                )
            )
        ).affirm();
    }

    @Test
    void dictionaryInDictionary() {
        new Assertion<>(
            "Must represent a dictionary in dictionary",
            new Dictionary()
                .add(
                    "ProcSet",
                    new Array("PDF", "Text", "ImageB", "ImageC", "ImageI")
                )
                .add(
                    "Font",
                    new Dictionary()
                        .add(
                            "F1",
                            new Dictionary()
                                .add("Type", new Name("Font"))
                                .add("BaseFont", new Name("Times-Roman"))
                                .add("Subtype", new Name("Type1"))
                        )
                ),
            new IsText(
                new Joined(
                    " ",
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    "/Font << /F1 << /Type /Font /BaseFont",
                    "/Times-Roman /Subtype /Type1 >> >> >>"
                )
            )
        ).affirm();
    }

    @Test
    void addElementInArrayInDictionary() {
        final Dictionary dictionary = new Dictionary()
            .add("Name1", new Name("Name2"))
            .add(
                "Name3",
                new Array(
                    new Name("Name4"),
                    new Int(123)
                )
            );
        final Array array = dictionary.get("Name3");
        final Dictionary expected = new Dictionary()
            .add("Name1", new Name("Name2"))
            .add("Name3", array.add(new Bool(false)));
        new Assertion<>(
            "Must add an element in array in dictionary",
            expected,
            new IsText("<< /Name1 /Name2 /Name3 [/Name4 123 false] >>")
        ).affirm();
    }

    @Test
    void dictionaryWithStream() throws Exception {
        final Bytes content = new BytesOf(
            new ResourceOf(
                "image/logo.png"
            )
        );
        final Stream stream = new Stream(content);
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write("<< /Length 2373 >>\nstream\n".getBytes());
        expected.write(content.asBytes());
        expected.write("\nendstream".getBytes());
        new Assertion<>(
            "Must represent a dictionary with a stream",
            new Dictionary()
                .add("Length", new Int(stream.length()))
                .with(stream).asBytes(),
            new IsEqual<>(expected.toByteArray())
        ).affirm();
    }

    @Test
    void dictionaryForImage() throws Exception {
        final Bytes image = new BytesOf(
            new ResourceOf(
                "image/logo.png"
            )
        );
        final ByteArrayInputStream bais = new ByteArrayInputStream(
            image.asBytes()
        );
        if (bais.skip(287) != 287) {
            throw new IllegalStateException("I can't skip 287 bytes");
        }
        final byte[] content = bais.readNBytes(2086);
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write(
            new Joined(
                " ",
                "<< /Type /XObject /Subtype /Image /Width 104 /Height 71",
                "/ColorSpace [/Indexed /DeviceRGB 63 6 0 R] /DecodeParms",
                "<< /Predictor 15 /Colors 1 /BitsPerComponent 8",
                "/Columns 104 >> /Mask [0 0] /Length 2086 >>\nstream\n"
            ).asString().getBytes()
        );
        expected.write(content);
        expected.write("\nendstream".getBytes());
        new Assertion<>(
            "Must represent a dictionary for an image",
            new Dictionary()
                .add("Type", new Name("XObject"))
                .add("Subtype", new Name("Image"))
                .add("Width", new Int(104))
                .add("Height", new Int(71))
                .add(
                    "ColorSpace",
                    new Array(
                        new Name("Indexed"),
                        new Name("DeviceRGB"),
                        new Int(63),
                        new Text("6 0 R")
                    )
                )
                .add(
                    "DecodeParms",
                    new Dictionary()
                        .add("Predictor", new Int(15))
                        .add("Colors", new Int(1))
                        .add("BitsPerComponent", new Int(8))
                        .add("Columns", new Int(104))
                )
                .add("Mask", new Array(new Int(0), new Int(0)))
                .add("Length", new Int(2086))
                .with(new Stream(content))
                .asBytes(),
            new IsEqual<>(expected.toByteArray())
        ).affirm();
    }

    @Test
    void merge() {
        final Dictionary procset = new Dictionary()
            .add(
                "ProcSet",
                new Array("PDF", "Text", "ImageB", "ImageC", "ImageI")
            );
        final Dictionary xobject = new Dictionary()
            .add("XObject", new Dictionary().add("I1", new Text("5 0 R")));
        final Dictionary merge = procset.merge(xobject);
        new Assertion<>(
            "Must merge two dictionaries",
            merge,
            new IsText(
                new Joined(
                    " ",
                    "<< /ProcSet [/PDF /Text /ImageB /ImageC /ImageI]",
                    "/XObject << /I1 5 0 R >> >>"
                )
            )
        ).affirm();
    }
}
