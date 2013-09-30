package ru.game.hat.support;

import static ru.game.hat.support.Lang.ENG;
import static ru.game.hat.support.Lang.RUS;

import java.io.File;
import java.util.List;
import java.util.Map;

public class WordSupport {
	// TODO use assets here!
	public static final String ROOT_DIR = "g:/project/notandroid/shlyapa/";
	public static final File RU_FOLDER = new File(ROOT_DIR + RUS.getDesc());
	public static final File EN_FOLDER = new File(ROOT_DIR + ENG.getDesc());
	public static final String DEFAULT_ENCODING = "utf-8";

	private static Words words = new Words(RUS);

	static {
		RU_FOLDER.mkdirs();
		EN_FOLDER.mkdirs();
	}
	/**
	 * Builder for word support
	 * @param wordsCount count words for one game
	 * @param level words complexity
	 * @param lang language of words
	 * @return support for one game
	 */
	public static Game newGame(int wordsCount, Level level, Lang lang) throws Exception {
		if (lang != words.getLang()) {
			words = new Words(lang);
		}
		final List<Word> wordList;
		try {
			wordList = words.wordsForGame(wordsCount, level);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get words", e);
		}
		return new Game(wordList);
	}

	public static final class Game {

		private final int gameSize;
		private final List<Word> words;
		private int index = 0;

		private Game(List<Word> words) {
			this.gameSize = words.size();
			this.words = words;
		}

		public boolean hasWord() {
			return index < gameSize;
		}

		public Word next() {
			final Word word = words.get(index);
			index += 1;
			return word;
		}
	}

	public static long wordToNumber(String value, Lang lang) {
		try {
			final String word = value.toUpperCase();
			final Map<Character, Integer> positions = lang.positions();
			long acc = 0;
			for (int i = 0; i < word.length(); i++) {
				final int digit = positions.get(word.charAt(i));
				acc = acc * positions.size() + digit;
			}
			return acc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
