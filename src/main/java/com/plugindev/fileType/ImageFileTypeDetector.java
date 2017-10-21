package com.plugindev.fileType;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.vfs.VirtualFile;
import org.intellij.images.fileTypes.ImageFileTypeManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This file type detector is able to detect JPEG files which have an file extension which isn't *.jpg.
 *
 * @author jansorg
 */
public class ImageFileTypeDetector implements FileTypeRegistry.FileTypeDetector {
    private static final Logger LOG = Logger.getInstance("com.plugindev.fileType");
    private static AtomicInteger CALLS = new AtomicInteger(0);
    private static byte[][] MAGIC_BYTES = new byte[][]{
            //taken from http://www.garykessler.net/library/file_sigs.html
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE2},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE8},
    };


    @Nullable
    @Override
    public FileType detect(@NotNull VirtualFile file, @NotNull ByteSequence firstBytesSequence, @Nullable CharSequence firstCharsIfText) {
        CALLS.incrementAndGet();
        LOG.warn(String.format("JPEG. Calls: %d, file: %s", CALLS.get(), file.getNameSequence()));

        if (firstCharsIfText == null && firstBytesSequence.getOffset() == 0) {
            for (byte[] bytes : MAGIC_BYTES) {
                if (firstBytesSequence.getLength() >= bytes.length) {
                    byte[] firstBytes = firstBytesSequence.getBytes();

                    int i = 0;
                    while (i < bytes.length && bytes[i] == firstBytes[i]) {
                        i++;
                    }

                    if (i == bytes.length) {
                        return ImageFileTypeManager.getInstance().getImageFileType();
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getVersion() {
        return 2;
    }
}
