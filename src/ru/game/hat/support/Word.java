package ru.game.hat.support;

public class Word {
	private final long id;
	private final String word;

	public Word(long id, String word) {
		this.id = id;
		this.word = word;
	}

	public long getId() {
		return id;
	}

	public String getWord() {
		return word;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Word word1 = (Word) o;

		if (word != null ? !word.equals(word1.word) : word1.word != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return word != null ? word.hashCode() : 0;
	}
}
