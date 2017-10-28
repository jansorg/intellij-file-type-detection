package com.plugindev.fileType;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.Assert;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @author jansorg
 */
public class JsonDataOverrideFactoryTest extends LightCodeInsightFixtureTestCase {
    public void testPlainTextOverride() {
        VirtualFile file = myFixture.copyFileToProject("json-data/mydata.unknown");
        Assert.assertEquals("The file type must be overridden to JSON", JsonDataOverrideFactory.JSON_OVERRIDE, file.getFileType());
    }

    public void testNoPlainTextOverride() {
        VirtualFile file = myFixture.copyFileToProject("jpeg-files/garrett-parker-247225.myjpeg");
        Assert.assertNotEquals("The file type must be overridden to plain text", JsonDataOverrideFactory.JSON_OVERRIDE, file.getFileType());
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