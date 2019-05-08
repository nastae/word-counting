package net.metasite.wordcounting.counting;

import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.word.Word;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WordCountingProcess {

    private DocumentReaderFactory factory;

    public WordCountingProcess(DocumentReaderFactory factory) {
        this.factory = factory;
    }

    public Map<String, Set<Word>> countWordsFromFiles(List<MultipartFile> files) {
         return getWordsFromFiles(files)
                .collect(Collectors.groupingByConcurrent(Word::getGroup, Collectors.toSet()));
    }

    private Stream<Word> getWordsFromFiles(List<MultipartFile> files) {
        return files.parallelStream()
                .map(this::readLinesFromFile)
                .flatMap(x-> x.map(Stream::of).orElseGet(Stream::empty))
                .flatMap(text -> Stream.of(text.split("\\W")))
                .map(Word::new);
    }

    private Optional<String> readLinesFromFile(MultipartFile file) {
        return factory.getDocumentReader(file.getOriginalFilename()).map(r -> {
            try (InputStream stream = file.getInputStream()) {
                return r.read(stream);
            } catch (IOException e) {
                return Optional.<String>empty();
            }
        }).orElseGet(Optional::empty);
    }
}