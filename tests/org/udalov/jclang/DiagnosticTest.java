package org.udalov.jclang;

import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticTest extends ClangTest {
    private final File file;
    private static final String EXPECTED_SUFFIX = ".expected";

    private DiagnosticTest(@NotNull File file) {
        this.file = file;
    }

    @Override
    protected void runTest() throws Throwable {
        Index index = createIndex(false, false);
        TranslationUnit unit = index.parseTranslationUnit(file.getPath(), 0);
        List<Diagnostic> diagnostics = unit.getDiagnostics();
        List<String> actual = serializeDiagnostics(diagnostics);
        List<String> expected = readExpectedDiagnostics();
        if (expected == null) {
            writeExpectedDiagnostics(actual);
            fail("Expected file wasn't found, it will be created");
        }
        assertEquals(expected, actual);
    }

    @Override
    @NotNull
    public String getName() {
        return file.getName();
    }

    @NotNull
    private List<String> serializeDiagnostics(@NotNull List<Diagnostic> diagnostics) {
        List<String> result = new ArrayList<String>(diagnostics.size());
        for (Diagnostic diagnostic : diagnostics) {
            String s = diagnostic.format(1 /* Display source location */);
            result.add(s.substring(s.lastIndexOf('/') + 1));
        }
        return result;
    }

    @Nullable
    private List<String> readExpectedDiagnostics() throws IOException {
        File expected = new File(file + EXPECTED_SUFFIX);
        if (!expected.exists()) return null;
        List<String> result = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(expected));
        String s;
        while ((s = reader.readLine()) != null) {
            result.add(s);
        }
        reader.close();
        return result;
    }

    private void writeExpectedDiagnostics(@NotNull List<String> diagnostics) throws IOException {
        File expected = new File(file + EXPECTED_SUFFIX);
        PrintWriter writer = new PrintWriter(expected);
        for (String diagnostic : diagnostics) {
            writer.println(diagnostic);
        }
        writer.close();
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        File dir = new File("testData/diagnostic");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().endsWith(EXPECTED_SUFFIX)) {
                    suite.addTest(new DiagnosticTest(file));
                }
            }
        }
        return suite;
    }
}
