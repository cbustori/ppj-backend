package com.enrealit.ppj.shared.dto;

public class FileUploadDto {

	private String contentType;
	private byte[] content;

	public FileUploadDto(String contentType, byte[] content) {
		this.contentType = contentType;
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public byte[] getContent() {
		return content;
	}

}
