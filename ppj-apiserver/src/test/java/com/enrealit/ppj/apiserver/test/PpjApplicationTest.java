package com.enrealit.ppj.apiserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.enrealit.ppj.apiserver.PpjApplication;
import com.enrealit.ppj.service.EventService;
import com.enrealit.ppj.service.PlaceService;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PpjApplication.class)
public class PpjApplicationTest {

	@Autowired
	private GraphQLTestTemplate graphQLTestTemplate;

	@MockBean
	private PlaceService placeService;

	@MockBean
	private EventService eventService;

	@Test
	public void findPlaceByIdTest() throws IOException {
		String id = "5da1a4c6c880e62410d942b8";
		String name = "Fernandez et Fernandez";
		RestaurantDto r = new RestaurantDto();
		r.setId(id);
		r.setName(name);
		when(placeService.findById(id)).thenReturn(r);
		GraphQLResponse response = graphQLTestTemplate.postForResource("place_test.graphql");
		assertTrue(response.isOk());
		assertEquals(id, response.get("$.data.place.id"));
		assertEquals(name, response.get("$.data.place.name"));
		assertEquals("Restaurant", response.get("$.data.place.__typename"));
	}

	@Test
	public void findEventByIdTest() throws IOException {
		String id = "5da1a4c6c880e62410d942b9";
		String description = "Taleggio Cheese";
		DishDto d = new DishDto();
		d.setId(id);
		d.setDescription(description);
		when(eventService.findById(id)).thenReturn(d);
		GraphQLResponse response = graphQLTestTemplate.postForResource("event_test.graphql");
		assertTrue(response.isOk());
		assertEquals(id, response.get("$.data.event.id"));
		assertEquals(description, response.get("$.data.event.description"));
		assertEquals("Dish", response.get("$.data.event.__typename"));
	}
}
