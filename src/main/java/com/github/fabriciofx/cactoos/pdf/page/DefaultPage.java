/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf.page;

import com.github.fabriciofx.cactoos.pdf.Content;
import com.github.fabriciofx.cactoos.pdf.Id;
import com.github.fabriciofx.cactoos.pdf.Indirect;
import com.github.fabriciofx.cactoos.pdf.Page;
import com.github.fabriciofx.cactoos.pdf.Resource;
import com.github.fabriciofx.cactoos.pdf.content.Contents;
import com.github.fabriciofx.cactoos.pdf.indirect.DefaultIndirect;
import com.github.fabriciofx.cactoos.pdf.resource.ProcSet;
import com.github.fabriciofx.cactoos.pdf.resource.Resources;
import com.github.fabriciofx.cactoos.pdf.text.Reference;
import com.github.fabriciofx.cactoos.pdf.type.Array;
import com.github.fabriciofx.cactoos.pdf.type.Dictionary;
import com.github.fabriciofx.cactoos.pdf.type.Name;
import com.github.fabriciofx.cactoos.pdf.type.Text;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * PageDefault.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class DefaultPage implements Page {
    /**
     * Object number.
     */
    private final int number;

    /**
     * Generation number.
     */
    private final int generation;

    /**
     * Resources.
     */
    private final Scalar<Resources> resources;

    /**
     * Page contents.
     */
    private final Contents contents;

    /**
     * Ctor.
     *
     * @param id Id number
     * @param contents Page contents
     */
    public DefaultPage(
        final Id id,
        final Contents contents
    ) {
        this(id.increment(), 0, id, contents);
    }

    /**
     * Ctor.
     *
     * @param number Object number
     * @param generation Generation number
     * @param id Id number
     * @param contents Page contents
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public DefaultPage(
        final int number,
        final int generation,
        final Id id,
        final Contents contents
    ) {
        this.number = number;
        this.generation = generation;
        this.contents = contents;
        this.resources = new Sticky<>(
            () -> {
                final List<Resource> rscrs = new ListOf<>();
                rscrs.add(new ProcSet());
                for (final Content content : contents) {
                    if (!content.resource().isEmpty()) {
                        rscrs.add(content.resource().get(0));
                    }
                }
                return new Resources(id.increment(), 0, rscrs);
            }
        );
    }

    @Override
    public Resources resources() {
        return new Unchecked<>(this.resources).value();
    }

    @Override
    public Contents contents() {
        return this.contents;
    }

    @Override
    public Indirect indirect(final int... parent) throws Exception {
        Array refs = new Array();
        for (final Content content : this.contents) {
            refs = refs.add(new Text(content.indirect().reference().asString()));
        }
        return new DefaultIndirect(
            this.number,
            this.generation,
            new Dictionary()
                .add("Type", new Name("Page"))
                .add(
                    "Resources",
                    new Text(this.resources().indirect().reference().asString())
                )
                .add("Contents", refs)
                .add("Parent", new Text(new Reference(parent[0], 0).asString()))
        );
    }

    @Override
    public void print(
        final List<Indirect> indirects,
        final int... parent
    ) throws Exception {
        indirects.add(this.indirect(parent));
        this.resources().print(indirects);
        this.contents().print(indirects);
    }
}
