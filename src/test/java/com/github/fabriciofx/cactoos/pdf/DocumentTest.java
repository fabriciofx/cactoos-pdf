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

import java.io.File;
import java.nio.file.Files;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Document}.
 *
 * @since 0.0.1
 */
final class DocumentTest {
    @Test
    void buildDocument() {
        final Count count = new AtomicCount();
        new Assertion<>(
            "Must represent a PDF document",
            new TextOf(
                new Document(
                    count,
                    new Metadata(count, "Hello World"),
                    new Catalog(
                        count,
                        new Pages(
                            count,
                            PageSize.A4,
                            new Page(
                                count,
                                new Resources(
                                    new Font(
                                        count,
                                        new FontFamily("Times-Roman", "Type1"),
                                        "F1"
                                    )
                                ),
                                new Contents(
                                    new Text(
                                        count,
                                        18,
                                        0,
                                        0,
                                        "Hello World with (, ), \\ and \r"
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            new IsText(
                new Joined(
                    "\n",
                    "%PDF-1.3\n%���������",
                    "1 0 obj\n<< /Title (Hello World) >>\nendobj",
                    "6 0 obj\n<< /Type /Catalog /Pages 5 0 R >>\nendobj",
                    "5 0 obj\n<< /Type /Pages /Kids [4 0 R] /Count 1 /MediaBox [0 0 595.28 841.89] >>\nendobj",
                    "4 0 obj\n<< /Type /Page /Resources 2 0 R /Contents 3 0 R /Parent 5 0 R >>\nendobj",
                    "2 0 obj\n<< /Font << /F1 << /Type /Font /BaseFont /Times-Roman /Subtype /Type1 >> >> >>\nendobj",
                    "3 0 obj\n<< /Length 62 >>\nstream\nBT /F1 18 Tf 0 0 Td (Hello World with \\(, \\), \\\\ and \\r) Tj ET\nendstream\nendobj",
                    "trailer << /Root 6 0 R /Size 7 >>",
                    "%%%%EOF"
                )
            )
        ).affirm();
    }

    @Disabled
    @Test
    void buildFile() throws Exception {
        final File file = new File("HelloWorld.pdf");
        final Count count = new AtomicCount();
        Files.write(
            file.toPath(),
            new Document(
                count,
                new Metadata(count, "Hello World"),
                new Catalog(
                    count,
                    new Pages(
                        count,
                        PageSize.A4,
                        new Page(
                            count,
                            new Resources(
                                new Font(
                                    count,
                                    new FontFamily("Times-Roman", "Type1"),
                                    "F1"
                                )
                            ),
                            new Contents(
                                new Text(
                                    count,
                                    18,
                                    0,
                                    0,
                                    "Hello World with (, ), \\ and \r"
                                )
                            )
                        )
                    )
                )
            ).asBytes()
        );
    }
}
