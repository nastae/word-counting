package net.metasite.wordcounting;


import com.google.common.collect.ImmutableList;
import net.metasite.wordcounting.counting.WordCountingProcess;
import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.word.Word;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DocumentReaderFactory.class, WordCountingProcess.class})
@EnableRuleMigrationSupport
public class WordCountingProcessIT {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Resource
    private WordCountingProcess process;

    @Test
    public void shouldCountWordsFromOneFile() throws IOException {
        Map<String, Set<Word>> wordsGroup = process.countWordsFromFiles(
                ImmutableList.of(createTextFile("file1.txt", "apple apple")));
        Assertions.assertEquals(wordsGroup.get("a-g").size(), 1);
        Assertions.assertEquals(wordsGroup.get("a-g").iterator().next().getCount(), 2);
    }

    @Test
    public void shouldCountWordsFromTwoFiles() throws IOException, InterruptedException {
        Map<String, Set<Word>> wordsGroup = process.countWordsFromFiles(
                ImmutableList.of(createTextFile("test1.txt", "apple Ananas"),
                        createTextFile("test2.txt", "apple apple"),
                        createTextFile("test3.txt", "apple apple")));
        System.out.println(wordsGroup);

        Assertions.assertEquals(wordsGroup.get("a-g").size(), 2);
    }


    @Test
    public void shouldCountWordsAndCollectByGroups() throws IOException, InterruptedException {
        Map<String, Set<Word>> wordsGroup = process.countWordsFromFiles(
                ImmutableList.of(createTextFile("file1.txt", "apple apple Ananas hire"),
                        createTextFile("file2.txt", "nested outline original"),
                        createTextFile("file3.txt", "Version version")));
        Assertions.assertEquals(wordsGroup.get("a-g").size(), 2);
        Assertions.assertEquals(wordsGroup.get("h-n").size(), 2);
        Assertions.assertEquals(wordsGroup.get("o-u").size(), 2);
        Assertions.assertEquals(wordsGroup.get("v-z").size(), 2);
    }

    @Test
    public void shouldNumbersAddToNoneGroup() throws IOException {
        Map<String, Set<Word>> wordsGroup = process.countWordsFromFiles(
                ImmutableList.of(createTextFile("test1.txt", "1337 420s 123")));
        Assertions.assertEquals(wordsGroup.size(), 1);
        Assertions.assertEquals(wordsGroup.get("none").size(), 3);
    }

    public MultipartFile createTextFile(String name, String content) throws IOException {
        File file = tempFolder.newFile(name);
        FileUtils.writeStringToFile(file, content);
        return new MockMultipartFile("file", file.getName(),
                "text/plain", IOUtils.toByteArray(new FileInputStream(file)));
    }
}