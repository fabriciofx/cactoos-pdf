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
package com.github.fabriciofx.cactoos.pdf.resource.font;

import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.resource.FontFamily;

/**
 * ZapfDingbats.
 *
 * @since 0.0.1
 */
public final class ZapfDingbats extends FontEnvelope {
    /**
     * Widths.
     */
    private static final int[] WIDTHS = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 278, 974, 961, 974, 980, 719, 789, 790,
        791, 690, 960, 939, 549, 855, 911, 933, 911, 945,
        974, 755, 846, 762, 761, 571, 677, 763, 760, 759,
        754, 494, 552, 537, 577, 692, 786, 788, 788, 790,
        793, 794, 816, 823, 789, 841, 823, 833, 816, 831,
        923, 744, 723, 749, 790, 792, 695, 776, 768, 792,
        759, 707, 708, 682, 701, 826, 815, 789, 789, 707,
        687, 696, 689, 786, 787, 713, 791, 785, 791, 873,
        761, 762, 762, 759, 759, 892, 892, 788, 784, 438,
        138, 277, 415, 392, 392, 668, 668, 0, 390, 390,
        317, 317, 276, 276, 509, 509, 410, 410, 234, 234,
        334, 334, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 732, 544, 544, 910, 667, 760, 760, 776, 595,
        694, 626, 788, 788, 788, 788, 788, 788, 788, 788,
        788, 788, 788, 788, 788, 788, 788, 788, 788, 788,
        788, 788, 788, 788, 788, 788, 788, 788, 788, 788,
        788, 788, 788, 788, 788, 788, 788, 788, 788, 788,
        788, 788, 894, 838, 1016, 458, 748, 924, 748, 918,
        927, 928, 928, 834, 873, 828, 924, 924, 917, 930,
        931, 463, 883, 836, 836, 867, 867, 696, 696, 874,
        0, 874, 760, 946, 771, 865, 771, 888, 967, 888,
        831, 873, 927, 970, 918, 0,
    };

    /**
     * Ctor.
     *
     * @param id Id number
     * @param size Font size in points
     */
    public ZapfDingbats(final Id id, final int size) {
        this(id.increment(), 0, size);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param size Font size in points
     */
    public ZapfDingbats(
        final int number,
        final int generation,
        final int size
    ) {
        super(
            number,
            new FontFamily(number, generation, "ZapfDingbats", "Type1"),
            size
        );
    }

    @Override
    public int width(final char chr) {
        return ZapfDingbats.WIDTHS[chr];
    }
}
