package com.plugindev.fileType;

import com.google.common.collect.Lists;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.fileTypes.impl.FileTypeManagerImpl;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author jansorg
 */
public class SwitchingFileTypeDetectorTest extends LightCodeInsightFixtureTestCase {
    public void testXmlFileType() {
        PsiFile file = myFixture.configureByText("myfile.unknownExt", "<?xml>\n<root>\n</root>");
        Assert.assertEquals(StdFileTypes.XML, file.getFileType());
        Assert.assertEquals(StdFileTypes.XML, file.getVirtualFile().getFileType());
    }

    public void testJavaFileType() {
        PsiFile file = myFixture.configureByText("myfile.unknownExt", "package foo;\n/* comment */");
        Assert.assertEquals(StdFileTypes.JAVA, file.getFileType());
        Assert.assertEquals(StdFileTypes.JAVA, file.getVirtualFile().getFileType());
    }

    public void testHtmlFileType() {
        PsiFile file = myFixture.configureByText("myfile.unknownExt", "<html>\n</html>");
        Assert.assertEquals(StdFileTypes.HTML, file.getFileType());
        Assert.assertEquals(StdFileTypes.HTML, file.getVirtualFile().getFileType());
    }

    public void testSwitching() throws Exception {
        VirtualFile file = myFixture.configureByText("myfile.unknownExt", "<?xml>").getVirtualFile();
        Assert.assertEquals(StdFileTypes.XML, file.getFileType());

        ApplicationManager.getApplication().runWriteAction(() -> FileDocumentManager.getInstance().getDocument(file).setText(""));
        myFixture.type("<html>");
        forceFileTypeUpdate(file);
        Assert.assertEquals(StdFileTypes.HTML, file.getFileType());

        ApplicationManager.getApplication().runWriteAction(() -> FileDocumentManager.getInstance().getDocument(file).setText(""));
        myFixture.type("package bar;");
        forceFileTypeUpdate(file);
        Assert.assertEquals(StdFileTypes.JAVA, file.getFileType());
    }

    //enforces the update of the file type. In a real environment this is triggered by a change to the VFS,
    //in unit testing mode it's not done and thus must be enforced to get the same behaviour as in a real environment
    private void forceFileTypeUpdate(VirtualFile file) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ApplicationManager.getApplication().runWriteAction(() -> {
            FileDocumentManager.getInstance().saveAllDocuments();
        });

        //call "private void reDetect(@NotNull Collection<VirtualFile> files)" of FileTypeManagerImpl
        FileTypeManagerImpl fileTypeManager = (FileTypeManagerImpl) FileTypeManager.getInstance();
        Method method = fileTypeManager.getClass().getDeclaredMethod("reDetect", Collection.class);
        method.setAccessible(true);
        method.invoke(fileTypeManager, Lists.newArrayList(file));
    }
}