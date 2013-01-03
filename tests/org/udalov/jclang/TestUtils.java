package org.udalov.jclang;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private TestUtils() {}

    @NotNull
    public static String loadFileContents(@NotNull String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s).append(LINE_SEPARATOR);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    @NotNull
    public static RuntimeException rethrow(@NotNull Exception e) {
        throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
}
