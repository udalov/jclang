package org.udalov.jclang;

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Index extends PointerType {
    // TODO: Set<TranslationUnit.Flag> options
    @NotNull
    public TranslationUnit parseTranslationUnit(@Nullable String sourceFilename, int options, @NotNull String... args)
            throws TranslationException {
        // TODO: dealloc
        TranslationUnit translationUnit = LibClang.I.parseTranslationUnit(this, sourceFilename, args, args.length, null, 0, options);
        if (translationUnit == null) {
            throw new TranslationException();
        }
        return translationUnit;
    }
}
