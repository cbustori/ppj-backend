package com.enrealit.ppj.data.model;

import java.time.LocalDate;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("scheduler")
@Document(collection = "scheduler")
public class Scheduler extends EntityBase {
	
	private LocalDate availableOn;
	
	@DBRef
	private Event event;
	
	@DBRef
	private Place place;

}
