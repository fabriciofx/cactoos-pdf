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

import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;

/**
 * PageDefault.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ExtendsObject")
public interface Page extends Object {
    /**
     * Build the Page dictionary.
     *
     * @param parent Pages parent
     * @return The page dictionary
     */
    Dictionary dictionary(Pages parent) throws Exception;

    /**
     * Page Resources.
     * @return Resources
     */
    Resources resources();

    /**
     * Page Contents.
     * @return Contents
     */
    Contents contents();

    /**
     * Build a PDF page.
     *
     * @param parent Pages parent
     * @return An array of bytes that represents a PDF page
     * @throws Exception if fails
     */
    byte[] with(Pages parent) throws Exception;
}
