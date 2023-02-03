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
@TypeAlias("happy_hour")
@Document(collection = "events")
public class HappyHour extends Event {

	private Integer startingTime;
	private Integer endingTime;

}
