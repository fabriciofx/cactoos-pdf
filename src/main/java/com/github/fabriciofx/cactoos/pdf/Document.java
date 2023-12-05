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
package com.github.fabriciofx.cactoos.pdf;

import java.io.ByteArrayOutputStream;
import org.cactoos.Bytes;
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
    private static final String EOF = "%%%%EOF";

    /**
     * PDF metadata.
     */
    private final Metadata metadata;

    /**
     * Catalog.
     */
    private final Catalog catalog;

    /**
     * Ctor.
     *
     * @param metadata Metadata
     * @param catalog Catalog
     */
    public Document(final Metadata metadata, final Catalog catalog) {
        this.metadata = metadata;
        this.catalog = catalog;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(
            new FormattedText(
                "%%PDF-%s\n",
                Document.VERSION
            ).asString().getBytes()
        );
        baos.write(Document.SIGNATURE);
        baos.write(this.metadata.asBytes());
        baos.write(this.catalog.asBytes());
        baos.write(
            new FormattedText(
                "trailer << /Root %s /Size 6 >>\n",
                this.catalog.reference()
            ).asString().getBytes()
        );
        baos.write(Document.EOF.getBytes());
        return baos.toByteArray();
    }
}
