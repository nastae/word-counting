package net.metasite.wordcounting.counting;

import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.word.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

@Service
public class WordCounting {

    @Autowired
    private DocumentReaderFactory factory;

    public HashMap<String, Set<Word>> countWordsFromFiles(List<MultipartFile> files) {
        return files.parallelStream()
                .map(this::readLinesFromFile)
                .flatMap(x-> x.map(Stream::of).orElseGet(Stream::empty))
                .flatMap(text -> Stream.of(text.split("\\W")))
                .map(Word::new)
                .collect(WordsGroup::new, WordsGroup::accept, WordsGroup::combine)
                .getWordsGroup();
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

