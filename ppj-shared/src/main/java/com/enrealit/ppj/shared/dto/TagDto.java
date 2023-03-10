package com.enrealit.ppj.shared.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TagDto extends BaseDto {

	private String label;
	private List<String> tags;

}
