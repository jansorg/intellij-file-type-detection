package com.plugindev.fileType;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Demonstration of a file type detector which switches the file type depending on the first bytes.
 * It return the XML file type for files beginning with '<?xml'.
 * It return the HTML file type for files beginning with '<html'.
 * It return the Java file type for files beginning with 'package'.
 * <p>
 * Of course, this is very basic support for these file types. It is meant as a demonstration how file type switching could be done.
 *
 * @author jansorg
 */
public class SwitchingFileTypeDetector implements FileTypeRegistry.FileTypeDetector {
    @Nullable
    @Override
    public FileType detect(@NotNull VirtualFile file, @NotNull ByteSequence firstBytes, @Nullable CharSequence firstCharsIfText) {
        if (firstCharsIfText != null) {
            if (firstCharsIfText.length() >= 5 && "<?xml".equals(firstCharsIfText.subSequence(0, 5).toString())) {
                return StdFileTypes.XML;
            }

            if (firstCharsIfText.length() >= 5 && "<html".equals(firstCharsIfText.subSequence(0, 5).toString())) {
                return StdFileTypes.HTML;
            }

            if (firstCharsIfText.length() >= 7 && "package".equals(firstCharsIfText.subSequence(0, 7).toString())) {
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
