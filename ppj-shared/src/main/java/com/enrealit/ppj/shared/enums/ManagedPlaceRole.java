package com.enrealit.ppj.shared.enums;

public enum ManagedPlaceRole {

	CONTRIBUTOR(0, "CONTRIBUTOR", "contributor"), MANAGER(4, "MANAGER", "manager");

	Integer code;
	String libelle;
	String _class;

	ManagedPlaceRole(Integer code, String lib, String _class) {
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

	public String toString() {
		return libelle;
	}

}
