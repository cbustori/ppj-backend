package com.enrealit.ppj.shared.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDto {

	private String id;
	private Date created;
	private Date lastModified;
	protected String _class;

}
