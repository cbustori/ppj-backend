package com.enrealit.ppj.shared.enums;

public enum DistanceUnit {

	KILOMETER(1, "K"), MILE(2, "M"), NAUTIC_MILE(3, "NM");

	Integer code;
	String unit;

	DistanceUnit(Integer code, String unit) {
		this.code = code;
		this.unit = unit;
	}

	String unit() {
		return unit;
	}

}
