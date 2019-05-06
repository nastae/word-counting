package net.metasite.wordcounting.counting;

import net.metasite.wordcounting.io.DocumentReader;
import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.word.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
public class WordCountingProcess {

    @Autowired
    private DocumentReaderFactory factory;

    public void countWordsFromFiles(HashMap<String, Set<Word>> wordsGroup, List<MultipartFile> files) {
        wordsGroup.putAll(files.parallelStream()
                                .map(this::readLinesFromFile)
                                .flatMap(x-> x.stream().flatMap(Stream::of))
                                .flatMap(text -> Stream.of(text.split("\\W")))
                                .map(Word::new)
                                .collect(WordsGroup::new, WordsGroup::accept, WordsGroup::combine)
                                .getWordsGroup());
    }

    private Optional<String> readLinesFromFile(MultipartFile file) {
        final Optional<DocumentReader> reader = factory.getDocumentReader(file.getOriginalFilename());
        if (reader.isPresent()) {
            try {
                return reader.get().read(file.getInputStream());
            } catch (IOException e) {
                return Optional.<String>empty();
            }
        } else {
            return Optional.<String>empty();
        }
    }

    class WordsGroup {

        private final HashMap<String, Set<Word>> wordsGroup;

        WordsGroup() {
            wordsGroup = new HashMap<>();
        }

        void accept(Word word) {
            if (word.getWord().matches("^([a-gA-G].*)")) {
                addToWordSet("a-g", word);
            } else if (word.getWord().matches("^([h-nH-N].*)")) {
                addToWordSet("h-n", word);
            } else if (word.getWord().matches("^([o-uO-U].*)")) {
                addToWordSet("o-u", word);
            } else if (word.getWord().matches("^([v-zV-Z].*)")) {
                addToWordSet("v-z", word);
            }
        }

        void combine(WordsGroup words) {
            putAllWords(words.getWordsGroup());
        }

        private void addToWordSet(String mapKey, Word word) {
            Set<Word> words = wordsGroup.get(mapKey);

            if (words == null) {
                words = Collections.synchronizedSet(new HashSet<Word>());
                words.add(word);
                wordsGroup.put(mapKey, words);
            } else {
                words.add(word);
            }
        }

        void putAllWords(HashMap<String, Set<Word>> wordsGroup) {
            this.wordsGroup.putAll(wordsGroup);
        }

        HashMap<String, Set<Word>> getWordsGroup() {
            return wordsGroup;
        }
    }
}

