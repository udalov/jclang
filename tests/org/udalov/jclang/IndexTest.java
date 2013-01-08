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

package org.udalov.jclang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.udalov.jclang.TestUtils.createOrCompare;

public class IndexTest extends ClangTest {
    @NotNull
    private String getDir() {
        return getTestDataDir() + "/index/";
    }

    @NotNull
    private String getTestDeclarationsFile() {
        return getDir() + "declarations.h";
    }

    @NotNull
    private TranslationUnit indexTestDeclarations(@NotNull IndexerCallback callback) {
        Index index = Clang.INSTANCE.createIndex(false, false);
        return index.indexSourceFile(callback, getTestDeclarationsFile(), new String[]{});
    }

    public void testIndexSourceFileWithEmptyCallback() {
        TranslationUnit unit = indexTestDeclarations(IndexerCallback.DO_NOTHING);
        assertTrue(unit.getDiagnostics().isEmpty());
    }

    public void testStartedTranslationUnit() {
        final boolean[] started = new boolean[] {false};
        indexTestDeclarations(new AbstractIndexerCallback() {
            @Override
            public void startedTranslationUnit() {
                assertFalse(started[0]);
                started[0] = true;
            }
        });
        assertTrue(started[0]);
    }

    public void testEnteredMainFile() {
        final File[] handle = new File[] {null};
        indexTestDeclarations(new AbstractIndexerCallback() {
            @Override
            public void enteredMainFile(@NotNull File mainFile) {
                handle[0] = mainFile;
            }
        });
        assertNotNull(handle[0]);
        assertEquals(new File(getTestDeclarationsFile()).getAbsoluteFile(), handle[0].getAbsoluteFile());
    }

    public void testIndexDeclaration() {
        StringWriter sw = new StringWriter();
        final PrintWriter out = new PrintWriter(sw);
        indexTestDeclarations(new AbstractIndexerCallback() {
            @Override
            public void indexDeclaration(@NotNull DeclarationInfo info) {
                Cursor cursor = info.getCursor();
                IndexLocation location = info.getLocation();
                out.print(location.getLine() + ":" + location.getColumn());
                out.print(" " + cursor.getKind().getSpelling());
                out.print(" " + cursor.getType().getKind());
                out.print(" " + nonEmptyCursorSpelling(cursor));
                out.println();

                EntityInfo entityInfo = info.getEntityInfo();
                out.println("  " + entityInfo.getUSR());
                out.println("  " + entityInfo.getKind());
                String spelling = cursor.getSpelling();
                assertEquals(spelling.isEmpty() ? null : spelling, entityInfo.getName());

                if (info.isRedeclaration()) out.println("  redecl");
                if (info.isDefinition()) out.println("  def");
                if (info.isContainer()) out.println("  cnt");
                if (info.isImplicit()) out.println("  implicit");
            }
        });
        out.close();
        createOrCompare(sw.toString(), getDir() + "indexDeclaration.txt");
    }

    @NotNull
    private String nonEmptyCursorSpelling(@NotNull Cursor cursor) {
        String spelling = cursor.getSpelling();
        return spelling.isEmpty() ? "<no-name>" : spelling;
    }

    public void testIndexObjCAttributes() {
        StringWriter sw = new StringWriter();
        final PrintWriter out = new PrintWriter(sw);
        Index index = Clang.INSTANCE.createIndex(false, false);
        index.indexSourceFile(new AbstractIndexerCallback() {
            @Override
            public void indexDeclaration(@NotNull DeclarationInfo info) {
                out.println(info.getCursor().getSpelling());
                for (IndexAttribute attribute : info.getAttributes()) {
                    IndexLocation location = attribute.getLocation();
                    out.print("  " + location.getLine() + ":" + location.getColumn());
                    out.print(" " + attribute.getKind());
                    out.print(" " + attribute.getCursor().getKind());
                    out.println();
                }
            }
        }, getDir() + "objcAttributes.h", new String[]{"-ObjC"});
        out.close();
        createOrCompare(sw.toString(), getDir() + "objcAttributes.txt");
    }

    public void testIndexContainerInfo() {
        StringWriter sw = new StringWriter();
        final PrintWriter out = new PrintWriter(sw);
        Index index = Clang.INSTANCE.createIndex(false, false);
        index.indexSourceFile(new AbstractIndexerCallback() {
            @Override
            public void indexDeclaration(@NotNull DeclarationInfo info) {
                out.println(nonEmptyCursorSpelling(info.getCursor()));
                printContainerCursor(info.getSemanticContainer());
                printContainerCursor(info.getLexicalContainer());
                printContainerCursor(info.getDeclAsContainer());
            }

            private void printContainerCursor(@Nullable ContainerInfo container) {
                if (container == null) return;
                Cursor cursor = container.getCursor();
                out.println("  " + cursor.getKind() + " " + nonEmptyCursorSpelling(cursor));
            }
        }, getDir() + "containerInfo.h", new String[]{"-c", "-x", "c++"});
        out.close();
        createOrCompare(sw.toString(), getDir() + "containerInfo.txt");
    }
}
