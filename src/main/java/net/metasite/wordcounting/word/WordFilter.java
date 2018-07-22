package net.metasite.wordcounting.word;
import java.util.Set;

public class WordFilter {
	
	private final static String[][] LETTERS = {{"A", "B", "C", "D", "E", "F", "G"}, 
													{"H", "I", "J", "K", "L", "M", "N"}, 
													{"O", "P", "Q", "R", "S", "T", "U"}, 
													{"V", "W", "X", "Y", "Z"}};

	public void filterSet (Set<TypedWord> set, String line) {
		String[] words = line.split("\\s+|[0-9]+|[^\\w]");//
		for (String word : words) {
			if (word.length() > 0) 
				addSet(set, word);
		}
	}
	
	private boolean addSet (Set<TypedWord> words, String word) {
		if (contains(word.substring(0, 1), LETTERS[0])) {
			if (!words.contains(word))
				return words.add(new TypedWord(word, 1, 0));
			
		} else if (contains(word.substring(0, 1), LETTERS[1])) {
			if (!words.contains(word))
				return words.add(new TypedWord(word, 1, 1));
			
		} else if (contains(word.substring(0, 1), LETTERS[2])) {
			if (!words.contains(word)) 
				return words.add(new TypedWord(word, 1, 2));
			
		} else if (contains(word.substring(0, 1), LETTERS[3])) {
			if (!words.contains(word)) 
				return words.add(new TypedWord(word, 1, 3));
		}
		return false;
	}
	
	private boolean contains (String symbol, String[] array) {
		boolean contains = false;
		if (array != null) {
			for (String element : array) {
				if (symbol.equals(element.toLowerCase()) || symbol.equals(element.toUpperCase())) {
					contains = true;
					break;
				}	
			}
		}
		return contains;
	}
}
