package com.plugindev.fileType;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * File type factory which registers the plain-text override detection.
 *
 * @author jansorg
 * @see JsonOverrideByParent
 */
public class JsonDataOverrideFactory extends FileTypeFactory {
    static final JsonOverrideByParent JSON_OVERRIDE = new JsonOverrideByParent();

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(JSON_OVERRIDE);
    }
}
