package com.enrealit.ppj.shared.enums;

public enum PlaceStatus {

	PENDING("PENDING", "En attente"), CONFIRMED("CONFIRMED", "Confirmé"), REFUSED("REFUSED", "Refusé");

	String code;
	String libelle;

	PlaceStatus(String code, String lib) {
		this.code = code;
		this.libelle = lib;
	}

}
