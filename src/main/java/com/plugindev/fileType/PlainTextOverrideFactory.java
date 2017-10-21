package com.plugindev.fileType;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author jansorg
 */
public class PlainTextOverrideFactory extends FileTypeFactory {

    public static final PlainTextOverrideByParent FILE_TYPE = new PlainTextOverrideByParent();

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(FILE_TYPE);
    }
}
