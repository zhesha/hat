package ru.game.hat.support;

import java.io.*;
import java.util.*;

import static ru.game.hat.support.WordSupport.DEFAULT_ENCODING;
import static ru.game.hat.support.WordSupport.ROOT_DIR;

public class Words {
	private final Lang lang;
	private List<Word> lowWords;
	private List<Word> normalWords;
	private List<Word> highWords;
	
	public Words(Lang lang) {
		this.lang = lang;
	}
	
	private List<Word> get(Level level) throws Exception {
		switch (level) {
			case HIGH:
				if (highWords == null || highWords.isEmpty()) {
					highWords = words(level);
				}
				return highWords;
			case NORMAL:
				if (normalWords == null || normalWords.isEmpty()) {
					normalWords = words(level);
				}
				return normalWords;
			case LOW:
				if (lowWords == null || lowWords.isEmpty()) {
					lowWords = words(level);
				}
				return lowWords;
		}
		throw new RuntimeException("unsupported level " + level);
	}

	/**
	 * Reads words for level from data file.
	 */
	private List<Word> words(Level level) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFileName(level)), DEFAULT_ENCODING));
			String line = reader.readLine();
			final List<Word> result = new ArrayList<Word>();

			String[] split = line.split("|");
			for (String val : split) {
				final List<String> pairs = Arrays.asList(val.split("~"));
				final Long id = Long.valueOf(pairs.get(0));
				final Word word = new Word(id, pairs.get(1));
				result.add(word);
			}

			Collections.shuffle(result);
			return result;
		} catch (IOException io) {
			throw io;
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse file " + getFileName(level), e);
		}
	}

	private String getFileName(Level level) {
		return ROOT_DIR + lang.getDesc() + "/" + level.desc();
	}

	public Lang getLang() {
		return lang;
	}

	public List<Word> wordsForGame(int wordsCount, Level level) throws Exception {
		List<Word> words = get(level);
		if (words.size() < wordsCount) {
			// puts all level words again
			words.clear();
			words = get(level);
		}

		final List<Word> toRemove = new ArrayList<Word>();
		final List<Word> result = new ArrayList<Word>();

		final int maxBound = words.size() - 1;
		int index = new Random().nextInt(maxBound);
		for (int i = 0; i < wordsCount; i++) {
			final Word word = words.get(index);
			toRemove.add(word);
			result.add(word);

			index = index + 1 == maxBound ? 0 : index + 1;
		}
		words.removeAll(toRemove);

		return result;
	}
}
