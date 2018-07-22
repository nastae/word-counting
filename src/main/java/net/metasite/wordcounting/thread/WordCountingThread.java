package net.metasite.wordcounting.thread;


import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Set;

import net.metasite.wordcounting.io.DocumentReader;
import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.word.TypedWord;
import net.metasite.wordcounting.word.WordFilter;

public class WordCountingThread implements Runnable {

	private WordFilter wordFilter = new WordFilter();
	private DocumentReaderFactory documentReaderFactory = new DocumentReaderFactory();
	
	private DocumentReader documentReader;
	private InputStream fileInputStream;
	private Set<TypedWord> words = null;

    public WordCountingThread (DocumentReader documentReader, InputStream fileInputStream, Set<TypedWord> words) {
    	this.documentReader = documentReader;
        this.fileInputStream = fileInputStream;
        this.words = words;
    }
	
	@Override
	public void run() {
		BufferedReader reader = null;
		
		String result = "";
		if (documentReader != null) {
			result = documentReader.read(fileInputStream);
		}
		
		wordFilter.filterSet(words, result);
	}

}

