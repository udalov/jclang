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

## Contributing

Contributions are very welcome. Nevertheless, there are certain rules/suggestions which should be followed before submitting a pull request:

* Launch `All Tests` before commiting to ensure you haven't broken anything
* Rebase instead of merge when fetching upstream changes to not make useless merge commits and keep the history clean and easy to inspect
* Annotate all method parameters and return types (except primitive types) with `@Nullable`/`@NotNull`: this helps in tracking down NPE problems
* For every newly added public method, there should be a test which directly or indirectly checks its behavior. Not really unit tests, but at least something to ensure it all works as expected
* Do not introduce new warnings in the code. Warnings include diagnostics from javac, as well as inspections in IDEA (the built-in plugin `Inspection Gadgets` should be turned on)

## License

jclang is licensed under the Apache License v2.0.
