package com.enrealit.ppj.data.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public abstract class EntityBase {

	@Id
	private String id;
	
	@CreatedDate
	private Date created;
	
	@LastModifiedDate
	private Date lastModified;
	
	protected String _class;

}
