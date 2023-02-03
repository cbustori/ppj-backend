package com.enrealit.ppj.shared.enums;

public enum EventType {

	DISH_OF_THE_DAY(1, "Plat du jour", "dish"), HAPPY_HOUR(2, "Happy Hour", "happy_hour"),
	CONCERT(3, "Concert", "concert"), OTHER(4, "Other", "other");

	Integer code;
	String libelle;
	String _class;

	EventType(Integer code, String lib, String _class) {
		this.code = code;
		this.libelle = lib;
		this._class = _class;
	}

	public String libelle() {
		return libelle;
	}

	public String _class() {
		return _class;
	}

}
