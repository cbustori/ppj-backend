package com.enrealit.ppj.shared.enums;

public enum UserStatus {

  EMAIL_TO_BE_CONFIRMED("EMAIL_TO_BE_CONFIRMED"), 
  RESTAURANT_TO_BE_CONFIRMED("RESTAURANT_TO_BE_CONFIRMED"),
  USER_FIRST_CONNECTION("USER_FIRST_CONNECTION"),
  USER_CONFIRMED("USER_CONFIRMED"),
  WAITING_FOR_VALIDATION("WAITING_FOR_VALIDATION");

  String code;

  UserStatus(String code) {
    this.code = code;
  }

  public String libelle() {
    return code;
  }

  @Override
  public String toString() {
    return code;
  }

}
