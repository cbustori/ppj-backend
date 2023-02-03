package com.enrealit.ppj.shared.enums;

public enum PlaceState {

	OPEN(1, "Ouvert"), CLOSED(2, "Fermé");

	Integer code;
	String libelle;

	PlaceState(Integer code, String lib) {
		this.code = code;
		this.libelle = lib;
	}

	public String libelle() {
		return libelle;
	}

}
