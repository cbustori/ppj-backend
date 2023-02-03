package com.enrealit.ppj.data.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("pub")
@Document(collection = "places")
public class Pub extends Place {

	private Boolean kitchen;
	private String nationality;

}
