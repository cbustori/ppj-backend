package com.enrealit.ppj.data.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "places")
@Builder
@TypeAlias("office")
public class Office extends Place {

}
