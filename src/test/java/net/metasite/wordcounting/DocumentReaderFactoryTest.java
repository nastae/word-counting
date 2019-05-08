package net.metasite.wordcounting;

import net.metasite.wordcounting.io.DocumentReaderFactory;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DocumentReaderFactoryTest {

    private DocumentReaderFactory factory = new DocumentReaderFactory();

    List<String> fileNames = Arrays.asList(".pdf", ".odt", ".ods", ".odp", ".doc",
            ".docx", ".xls", ".txt", ".html", ".xml", ".epub");

    @Test
    public void shouldCreateReaderFromDefinedFileNames() {
        for (String fileName : fileNames) {

            Assertions.assertTrue(factory.getDocumentReader(fileName).isPresent());
        }
    }

    @Test
    public void shouldNotCreateReaderWhenFileNameIsEmpty() {
        Assertions.assertFalse(factory.getDocumentReader(StringUtils.EMPTY).isPresent());
    }
}