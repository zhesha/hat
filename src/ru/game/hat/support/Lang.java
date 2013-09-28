package ru.game.hat.support;

import java.util.HashMap;
import java.util.Map;

public enum Lang {
	RUS("rus") {
		private final Map<Character, Integer> ruPositions = new HashMap<Character, Integer>();
		{
			final String ruSymbols = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß-";
			for (int i = 0; i < ruSymbols.length(); i++) {
				ruPositions.put(ruSymbols.charAt(i), i);
			}
		}
		@Override public Map<Character, Integer> positions() {
			return ruPositions;
		}
	}, ENG("eng") {
		private final Map<Character, Integer> enPositions = new HashMap<Character, Integer>();
		{
			final String enSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-";
			for (int i = 0; i < enSymbols.length(); i++) {
				enPositions.put(enSymbols.charAt(i), i);
			}
		}
		@Override public Map<Character, Integer> positions() {
			return enPositions;
		}
	};

	private final String desc;

	public String getDesc() {
		return desc;
	}

	private Lang(String desc) {
		this.desc = desc;
	}

	public abstract Map<Character, Integer> positions();
}
