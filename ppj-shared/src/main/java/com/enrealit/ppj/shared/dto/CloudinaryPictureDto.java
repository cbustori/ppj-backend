package com.enrealit.ppj.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudinaryPictureDto {

	private String url;
	private String publicId;

	@Override
	public String toString() {
		return "{\"url\": \"" + url + "\", \"public_id\": \"" + publicId + "\" }";
	}

}
