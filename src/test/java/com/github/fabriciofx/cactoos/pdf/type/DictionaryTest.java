/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.Joined;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Dictionary}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.AvoidDuplicateLiterals",
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
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
        expected.write(
            "<< /Length 2373 >>\nstream\n".getBytes(StandardCharsets.UTF_8)
        );
        expected.write(content.asBytes());
        expected.write("\nendstream".getBytes(StandardCharsets.UTF_8));
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
        final ByteArrayInputStream stream = new ByteArrayInputStream(
            image.asBytes()
        );
        if (stream.skip(287) != 287) {
            throw new IllegalStateException("I can't skip 287 bytes");
        }
        final byte[] content = new BytesOf(new InputOf(stream)).asBytes();
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write(
            new Joined(
                " ",
                "<< /Type /XObject /Subtype /Image /Width 104 /Height 71",
                "/ColorSpace [/Indexed /DeviceRGB 63 6 0 R] /DecodeParms",
                "<< /Predictor 15 /Colors 1 /BitsPerComponent 8",
                "/Columns 104 >> /Mask [0 0] /Length 2086 >>\nstream\n"
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        expected.write(content);
        expected.write("\nendstream".getBytes(StandardCharsets.UTF_8));
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
    void merge() throws Exception {
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

    @Test
    void dictionaryUpdateStream() throws Exception {
        final String hello = "Hello World!";
        final String msg = "New message, with a new Hello World!";
        final Dictionary dictionary = new Dictionary()
            .add("Length", new Int(hello.length()))
            .with(new Stream(hello.getBytes(StandardCharsets.UTF_8)));
        final Dictionary changed = dictionary
            .add("Length", new Int(msg.length()))
            .with(new Stream(msg.getBytes(StandardCharsets.UTF_8)));
        final Int length = changed.get("Length");
        new Assertion<>(
            "Must update a stream in a dictionary",
            length.value(),
            new IsNumber(36)
        ).affirm();
    }
}
