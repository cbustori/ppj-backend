package com.enrealit.ppj.shared.enums;

public enum PlaceType {

	RESTAURANT("RESTAURANT", "Restaurant", "restaurant"), PUB("PUB", "Pub", "pub"),
	OFFICE("OFFICE", "Office", "office"), HOTEL("HOTEL", "Hotel", "hotel");

	String code;
	String libelle;
	String _class;

	PlaceType(String code, String lib, String _class) {
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
