package com.enrealit.ppj.shared.enums;

public enum SocialType {
  LOCAL("LOCAL"), FACEBOOK("FACEBOOK"), GOOGLE("GOOGLE");

  String code;

  SocialType(String code) {
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
