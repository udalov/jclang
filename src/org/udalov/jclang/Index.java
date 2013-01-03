package org.udalov.jclang;

import com.sun.jna.PointerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.udalov.jclang.TranslationUnit.Flag;

public class Index extends PointerType {
    @NotNull
    public TranslationUnit parseTranslationUnit(@Nullable String sourceFilename, @NotNull String[] args, @NotNull Flag... options)
            throws TranslationException {
        int flags = 0;
        for (Flag option : options) {
            flags |= 1 << option.ordinal();
        }
        // TODO: dealloc
        TranslationUnit translationUnit = LibClang.I.parseTranslationUnit(this, sourceFilename, args, args.length, null, 0, flags);
        if (translationUnit == null) {
            throw new TranslationException();
        }
        return translationUnit;
    }
}
