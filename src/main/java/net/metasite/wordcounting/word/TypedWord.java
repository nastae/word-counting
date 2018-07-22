package net.metasite.wordcounting.word;

public class TypedWord extends Word {
	int type;
	
	public TypedWord(String word, int count, int type) {
		super(word, count);
		this.type = type;
	}
	
	public TypedWord(TypedWord typedWord) {
		super(typedWord.getWord(), typedWord.getCount());
		this.type = typedWord.getType();
	}

	public int getType() {
		return type;
	}

}
