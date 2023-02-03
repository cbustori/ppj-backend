package com.enrealit.ppj.data.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.enrealit.ppj.shared.enums.ManagedPlaceRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagedPlace {

	@DBRef
	private Place place;
	private ManagedPlaceRole role;

}
