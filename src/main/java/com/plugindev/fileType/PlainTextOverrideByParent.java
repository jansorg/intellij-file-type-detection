package com.plugindev.fileType;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.file.exclude.EnforcedPlainTextFileTypeFactory;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import org.codehaus.groovy.runtime.StackTraceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jansorg
 */
class PlainTextOverrideByParent extends LanguageFileType implements FileTypeIdentifiableByVirtualFile {
    private static final Logger LOG = Logger.getInstance("com.plugindev.fileType");
    private static AtomicInteger CALLS = new AtomicInteger(0);

    /**
     * Creates a language file type for the specified language.
     */
    PlainTextOverrideByParent() {
        super(PlainTextLanguage.INSTANCE);
    }

    @Override
    public boolean isMyFileType(@NotNull VirtualFile file) {
        CALLS.incrementAndGet();
        LOG.warn(String.format("plain text. Calls: %d, file: %s", CALLS.get(), file.getNameSequence()));

        try {
            throw new IllegalStateException();
        } catch (IllegalStateException e) {
            e.printStackTrace(System.err);
        }

        VirtualFile parent = file.getParent();
        return parent != null && parent.getName().equalsIgnoreCase("plain-text");
    }

    @NotNull
    @Override
    public String getName() {
        return "plain text override";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Plain text file in a plain-text directory";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return ".txt";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return EnforcedPlainTextFileTypeFactory.ENFORCED_PLAIN_TEXT_ICON;
    }
}
