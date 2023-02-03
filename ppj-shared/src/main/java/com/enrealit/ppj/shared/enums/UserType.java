package com.enrealit.ppj.shared.enums;

public enum UserType {

  CONTRIBUTOR("CONTRIBUTOR", "contributor"), 
  SUBSCRIBER("SUBSCRIBER", "subscriber"), 
  ADMIN("ADMIN", "administrator"), 
  MANAGER("MANAGER", "manager");

  String code;
  String _class;

  UserType(String code, String _class) {
    this.code = code;
    this._class = _class;
  }

  public String getRole() {
    return new StringBuilder().append("ROLE_").append(this.code).toString();
  }

  public String libelle() {
    return code;
  }

  public String _class() {
    return _class;
  }

  public String toString() {
    return code;
  }

}
