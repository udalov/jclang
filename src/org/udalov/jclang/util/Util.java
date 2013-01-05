/*
 * Copyright 2013 Alexander Udalov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.udalov.jclang.util;

import org.jetbrains.annotations.NotNull;

public class Util {
    private Util() {}

    public static <E extends Enum<E>> int buildOptionsMask(@NotNull E... values) {
        int result = 0;
        for (E value : values) {
            result |= 1 << value.ordinal();
        }
        return result;
    }
}
