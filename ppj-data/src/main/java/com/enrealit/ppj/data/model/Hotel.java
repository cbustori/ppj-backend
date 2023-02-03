package com.enrealit.ppj.data.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@TypeAlias("hotel")
@Document(collection = "places")
public class Hotel extends Place {

	private Integer nbStars;

}
