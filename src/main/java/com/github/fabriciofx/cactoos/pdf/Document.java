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
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;

/**
 * Document.
 *
 * @since 0.0.1
 */
public final class Document implements Bytes {
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
        final List<Indirect> indirects = new ListOf<>();
        this.information.print(indirects);
        this.catalog.print(indirects);
        final Header header = new Header();
        output.write(header.value());
        for (final Indirect indirect : indirects) {
            output.write(indirect.asBytes());
        }
        output.write(
            new CrossReference(
                this.id,
                this.information,
                this.catalog,
                header,
                indirects
            ).value()
        );
        output.write(Document.EOF.getBytes(StandardCharsets.UTF_8));
        return output.toByteArray();
    }

    /**
     * PDF Header.
     *
     * @since 0.0.1
     */
    private static final class Header implements Scalar<byte[]> {
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

        @Override
        public byte[] value() throws Exception {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(
                new FormattedText(
                    "%%PDF-%s\n",
                    Locale.ENGLISH,
                    Header.VERSION
                ).asString().getBytes(StandardCharsets.UTF_8)
            );
            out.write(Header.SIGNATURE);
            return out.toByteArray();
        }
    }

    /**
     * PDF Cross-Reference Table.
     *
     * @since 0.0.1
     */
    private static final class CrossReference implements Scalar<byte[]> {
        /**
         * Object Id.
         */
        private final Id id;

        /**
         * Information.
         */
        private final Information information;

        /**
         * Catalog.
         */
        private final Catalog catalog;

        /**
         * Header.
         */
        private final Header header;

        /**
         * Indirects.
         */
        private final List<Indirect> indirects;

        /**
         * Ctor.
         * @param id Object id
         * @param information Information
         * @param catalog Catalog
         * @param header Document header
         * @param indirects Indirects list
         * @checkstyle ParameterNumberCheck (10 lines)
         */
        CrossReference(
            final Id id,
            final Information information,
            final Catalog catalog,
            final Header header,
            final List<Indirect> indirects
        ) {
            this.id = id;
            this.information = information;
            this.catalog = catalog;
            this.header = header;
            this.indirects = indirects;
        }

        @Override
        public byte[] value() throws Exception {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(
                new FormattedText(
                    "xref\n0 %d\n0000000000 65535 f\n",
                    Locale.ENGLISH,
                    this.id.value()
                ).asString().getBytes(StandardCharsets.UTF_8)
            );
            int total = this.header.value().length;
            final int[] table = new int[this.id.value() - 1];
            for (int idx = 0; idx < this.indirects.size(); ++idx) {
                final int number = this.indirects.get(idx).reference()
                    .number() - 1;
                total = total + this.indirects.get(idx).asBytes().length;
                int sum = this.header.value().length;
                for (int count = 0; count < idx; ++count) {
                    sum = sum + this.indirects.get(count).asBytes().length;
                }
                table[number] = sum;
            }
            for (final int value : table) {
                out.write(
                    new FormattedText(
                        "%010d 00000 n\n",
                        Locale.ENGLISH,
                        value
                    ).asString().getBytes(StandardCharsets.UTF_8)
                );
            }
            out.write(
                new Trailer(this.id, this.information, this.catalog).value()
            );
            out.write(
                new FormattedText(
                    "startxref\n%d\n",
                    Locale.ENGLISH,
                    total
                ).asString().getBytes(StandardCharsets.UTF_8)
            );
            return out.toByteArray();
        }
    }

    /**
     * PDF Trailer.
     *
     * @since 0.0.1
     */
    private static final class Trailer implements Scalar<byte[]> {
        /**
         * Object Id.
         */
        private final Id id;

        /**
         * Information.
         */
        private final Information information;

        /**
         * Catalog.
         */
        private final Catalog catalog;

        /**
         * Ctor.
         *
         * @param id Object id
         * @param information Information
         * @param catalog Catalog
         */
        Trailer(
            final Id id,
            final Information information,
            final Catalog catalog
        ) {
            this.id = id;
            this.information = information;
            this.catalog = catalog;
        }

        @Override
        public byte[] value() throws Exception {
            return new FormattedText(
                "trailer\n<< /Size %d /Root %s /Info %s >>\n",
                Locale.ENGLISH,
                this.id.value(),
                this.catalog.indirect().reference().asString(),
                this.information.indirect().reference().asString()
            ).asString().getBytes(StandardCharsets.UTF_8);
        }
    }
}
