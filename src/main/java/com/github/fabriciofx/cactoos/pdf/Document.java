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
package com.github.fabriciofx.cactoos.pdf;

import com.github.fabriciofx.cactoos.pdf.object.Catalog;
import com.github.fabriciofx.cactoos.pdf.object.Information;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;

/**
 * Document.
 *
 * @since 0.0.1
 */
public final class Document implements Bytes {
    /**
     * PDF Version.
     */
    private static final String VERSION = "1.3";

    /**
     * PDF binary file signature.
     */
    private static final byte[] SIGNATURE = {
        (byte) 0x25, (byte) 0xc4, (byte) 0xe5, (byte) 0xf2, (byte) 0xe5,
        (byte) 0xeb, (byte) 0xa7, (byte) 0xf3, (byte) 0xa0, (byte) 0xd0,
        (byte) 0xc4, (byte) 0xc6, (byte) 0x0a,
    };

    /**
     * PDF end of file.
     */
    private static final String EOF = "%%EOF";

    /**
     * Id.
     */
    private final Id id;

    /**
     * PDF metadata.
     */
    private final Information information;

    /**
     * Catalog.
     */
    private final Catalog catalog;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param information Information
     * @param pages Pages
     */
    public Document(
        final Id id,
        final Information information,
        final Pages pages
    ) {
        this(id, information, new Catalog(id, pages));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param pages Pages
     */
    public Document(final Id id, final Pages pages) {
        this(id, new Information(id), new Catalog(id, pages));
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param catalog Catalog
     */
    public Document(final Id id, final Catalog catalog) {
        this(id, new Information(id), catalog);
    }

    /**
     * Ctor.
     *
     * @param id Id number
     * @param information Metadata
     * @param catalog Catalog
     */
    public Document(
        final Id id,
        final Information information,
        final Catalog catalog
    ) {
        this.id = id;
        this.information = information;
        this.catalog = catalog;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(
            new FormattedText(
                "%%PDF-%s\n",
                Locale.ENGLISH,
                Document.VERSION
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        output.write(Document.SIGNATURE);
        final List<Indirect> indirects = new ListOf<>();
        this.information.print(indirects);
        this.catalog.print(indirects);
        for (final Indirect indirect : indirects) {
            output.write(indirect.asBytes());
        }
        output.write(
            new FormattedText(
                "trailer << /Root %s /Size %d /Info %s >>\n",
                Locale.ENGLISH,
                this.catalog.indirect().reference().asString(),
                this.id.value(),
                this.information.indirect().reference().asString()
            ).asString().getBytes(StandardCharsets.UTF_8)
        );
        output.write(Document.EOF.getBytes(StandardCharsets.UTF_8));
        return output.toByteArray();
    }
}
