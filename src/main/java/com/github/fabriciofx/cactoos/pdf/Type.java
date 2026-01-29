/*
 * SPDX-FileCopyrightText: Copyright (C) 2023-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.pdf;

import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Type.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> The value of type.
 * @since 0.0.1
 */
public interface Type<T> extends Scalar<T>, Text, Bytes {
}
