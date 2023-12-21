package com.github.fabriciofx.cactoos.pdf.content;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Count;
import com.github.fabriciofx.cactoos.pdf.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Int;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Stream;
import java.io.ByteArrayOutputStream;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.UncheckedText;

public final class Image implements Content {
    private final int number;
    private final int generation;
    private final String name;
    private final PngImage png;

    public Image(final Count count, final String name, final PngImage png) {
        this(count.increment(), 0, name, png);
    }

    public Image(
        final int number,
        final int generation,
        final String name,
        final PngImage png
    ) {
        this.number = number;
        this.generation = generation;
        this.name = name;
        this.png = png;
    }

    public String name() {
        return this.name;
    }

    @Override
    public byte[] stream() throws Exception {
        return new FormattedText(
            new Joined(
                "\n",
                "2 J",
                "0.57 w",
                "q 85.04 0 0 58.06 28.35 766.83 cm /%s Do Q"
            ),
            this.name
        ).asString().getBytes();
    }

    @Override
    public Dictionary dictionary() throws Exception {
        final byte[] stream = this.stream();
        return new Dictionary()
            .add("Length", new Int(stream.length))
            .with(new Stream(stream));
    }

    @Override
    public Reference reference() {
        return new Reference(this.number, this.generation);
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%d %d obj\n",
                this.number,
                this.generation
            ).asString().getBytes()
        );
        baos.write(this.dictionary().asBytes());
        baos.write("endobj\n".getBytes());
        baos.write(this.png.asBytes());
        return baos.toByteArray();
    }
}
