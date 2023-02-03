package com.enrealit.ppj.data.model.user;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@TypeAlias("manager")
public class Manager extends User {

}
