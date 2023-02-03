package com.enrealit.ppj.apiserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.HotelDto;
import com.enrealit.ppj.shared.dto.place.OfficeDto;
import com.enrealit.ppj.shared.dto.place.PubDto;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;

import graphql.kickstart.tools.SchemaParserDictionary;

@Configuration
public class ParserConfiguration {

	@Bean
	public SchemaParserDictionary schemaParserDictionary() {
		Map map = new HashMap<>();
		map.put("Restaurant", RestaurantDto.class);
		map.put("Hotel", HotelDto.class);
		map.put("Dish", DishDto.class);
		map.put("HappyHour", HappyHourDto.class);
		map.put("Office", OfficeDto.class);
		map.put("Pub", PubDto.class);
		map.put("Concert", ConcertDto.class);

		return new SchemaParserDictionary().add(map);
	}

}
