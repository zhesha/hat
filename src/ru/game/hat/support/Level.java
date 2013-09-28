package ru.game.hat.support;

public enum Level {
	HIGH("high"),
	NORMAL("normal"),
	LOW("low");

	private final String desc;

	private Level(String desc) {
		this.desc = desc;
	}

	public String desc() {
		return desc;
	}
}
