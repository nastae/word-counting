package net.metasite.wordcounting.word;

public class Word {
	private String word;
	private int count;

	public Word(String word) {
		this(word, 1);
	}

	public Word(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increaseCount() {
		this.count++;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Word) {
			((Word) obj).increaseCount();
		}
		return this.hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		return getWord().hashCode();
	}
	
	public String getFirstSymbol () {
		if (word != null && word.length() > 0) {
			return word.substring(0, 1);
		}
		return "";
	}

	@Override
	public String toString() {
		return getWord() + " " + getCount();
	}
}