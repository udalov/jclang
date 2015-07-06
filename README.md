# jclang

Java bindings for [libclang](http://clang.llvm.org/doxygen/group__CINDEX.html).

**Warning**: the project is in the very early development stage, and a lot is not implemented yet. In particular, usage examples exist only in the form of small tests (found in `tests/`).

Contributions are welcome!

## Synopsis

jclang provides efficient, object-oriented and easy-to-use API for [Clang compiler](http://clang.llvm.org) in Java. 

jclang uses [JNA](http://github.com/twall/jna) to map native methods to their analogues in Java. On top of the JNA mapping (which is really just plain C functions), it provides a set of wrappers to call libclang methods conveniently and somewhat more object-oriented.

The following program prints information about all declarations found in `test.h` to stdout and prints all diagnostics reported by Clang to stderr:
```java
public static void main(String[] args) {
    Index index = Clang.INSTANCE.createIndex(false, false);

    TranslationUnit tu = index.indexSourceFile(new AbstractIndexerCallback() {
        @Override
        public void indexDeclaration(@NotNull DeclarationInfo info) {
            Cursor cursor = info.getCursor();
            IndexLocation location = info.getLocation();
            System.out.println(
                location.getLine() + ":" + location.getColumn() + " " +
                cursor.getKind().getSpelling() + " " +
                cursor.getType().getKind() + " " +
                cursor.getSpelling()
            );
        }
    }, "test.h", args);

    for (Diagnostic diagnostic : tu.getDiagnostics()) {
        System.err.println(diagnostic.format(Diagnostic.DisplayOptions.DISPLAY_SOURCE_LOCATION));
    }
}
```

## Building

The easiest way to build the project is to open it in [IntelliJ IDEA](http://www.jetbrains.com/idea/) and launch 'Make'.

To run tests, use `All Tests` configuration inside IDEA.

## License

jclang is licensed under the Apache License v2.0.
