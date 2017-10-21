package com.plugindev.fileType;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.intellij.images.fileTypes.ImageFileTypeManager;
import org.junit.Assert;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @author jansorg
 */
public class ImageFileTypeDetectorTest extends LightCodeInsightFixtureTestCase {
    public void testValidJpegFiles() {
        VirtualFile file = myFixture.copyFileToProject("jpeg-files/garrett-parker-247225.myjpeg");
        Assert.assertEquals("The file must be detected as a JPEG file.", ImageFileTypeManager.getInstance().getImageFileType(), file.getFileType());
    }

    @Override
    protected String getTestDataPath() {
        try {
            return Paths.get(getClass().getResource("/test-resource-marker.txt").toURI()).getParent().toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Can't locate test resources");
        }
    }
}