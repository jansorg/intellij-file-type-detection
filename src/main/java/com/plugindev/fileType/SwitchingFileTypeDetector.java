package com.plugindev.fileType;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author jansorg
 */
public class SwitchingFileTypeDetector implements FileTypeRegistry.FileTypeDetector  {
    @Nullable
    @Override
    public FileType detect(@NotNull VirtualFile file, @NotNull ByteSequence firstBytes, @Nullable CharSequence firstCharsIfText) {
        if (firstCharsIfText != null) {
            if ("<?xml".equals(firstCharsIfText.subSequence(0, 5).toString())) {
                return StdFileTypes.XML;
            }

            if ("<html".equals(firstCharsIfText.subSequence(0, 5).toString())) {
                return StdFileTypes.HTML;
            }

            if ("package".equals(firstCharsIfText.subSequence(0, 7).toString())) {
                return StdFileTypes.JAVA;
            }
        }

        return null;
    }

    @Override
    public int getVersion() {
        return 31;
    }
}
