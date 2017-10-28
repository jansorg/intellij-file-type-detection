package com.plugindev.fileType;

import com.intellij.json.JsonLanguage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.file.exclude.EnforcedPlainTextFileTypeFactory;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Marks all files which are directly beneath a directory named "json-data" as JSON files.
 *
 * @author jansorg
 */
class JsonOverrideByParent extends LanguageFileType implements FileTypeIdentifiableByVirtualFile {
    private static final Logger LOG = Logger.getInstance("com.plugindev.fileType");
    private static AtomicInteger CALLS = new AtomicInteger(0);

    /**
     * Creates a language file type for the specified language.
     */
    JsonOverrideByParent() {
        super(JsonLanguage.INSTANCE);
    }

    @Override
    public boolean isMyFileType(@NotNull VirtualFile file) {
        CALLS.incrementAndGet();
        LOG.warn(String.format("plain text. Calls: %d, file: %s", CALLS.get(), file.getNameSequence()));

        VirtualFile parent = file.getParent();
        return parent != null && "json-data".equalsIgnoreCase(parent.getName());
    }

    @NotNull
    @Override
    public String getName() {
        return "json override";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "JSON file in a json-data directory";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return EnforcedPlainTextFileTypeFactory.ENFORCED_PLAIN_TEXT_ICON;
    }
}
