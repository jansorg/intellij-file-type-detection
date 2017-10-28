package com.plugindev.fileType;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * File type factory which registers the plain-text override detection.
 *
 * @author jansorg
 * @see PlainTextOverrideByParent
 */
public class PlainTextOverrideFactory extends FileTypeFactory {
    static final PlainTextOverrideByParent FILE_TYPE = new PlainTextOverrideByParent();

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(FILE_TYPE);
    }
}
