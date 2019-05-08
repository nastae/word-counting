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

	public void increaseCount() {
		this.count++;
	}

	public String getGroup() {
		if (word.matches("^([a-gA-G].*)")) {
			return "a-g";
		} else if (word.matches("^([h-nH-N].*)")) {
			return "h-n";
		} else if (word.matches("^([o-uO-U].*)")) {
			return "o-u";
		} else if (word.matches("^([v-zV-Z].*)")) {
			return "v-z";
		}
		return "none";
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

	@Override
	public String toString() {
		return getWord() + " " + getCount();
	}

}