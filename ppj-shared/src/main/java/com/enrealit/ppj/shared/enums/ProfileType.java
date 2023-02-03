package com.enrealit.ppj.shared.enums;

public enum ProfileType {

	PRO("PRO", "Pro"), PRO_PLUS("PRO_PLUS", "Pro +"), PREMIUM("PREMIUM", "Premium");

	String code;
	String libelle;

	ProfileType(String code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}

	public static ProfileType fromCode(String code) {
		if (code == null) {
			return null;
		}
		for (ProfileType pt : ProfileType.values()) {
			if (pt.code().equals(code)) {
				return pt;
			}
		}
		return null;
	}

	public String code() {
		return code;
	}

	public String libelle() {
		return libelle;
	}
}
