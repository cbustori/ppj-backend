package com.enrealit.ppj.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.Tag;
import com.enrealit.ppj.data.repository.TagRepository;
import com.enrealit.ppj.shared.dto.TagDto;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private ModelMapper mapper;

	public TagDto findByLabel(String label) {
		return convertToDto(tagRepository.findByLabel(label));
	}

	public TagDto save(TagDto d) {
		return convertToDto(tagRepository.save(convertToEntity(d)));
	}

	private TagDto convertToDto(Tag tag) {
		return tag != null ? mapper.map(tag, TagDto.class) : null;
	}

	private Tag convertToEntity(TagDto tag) {
		return tag != null ? mapper.map(tag, Tag.class) : null;
	}

}
