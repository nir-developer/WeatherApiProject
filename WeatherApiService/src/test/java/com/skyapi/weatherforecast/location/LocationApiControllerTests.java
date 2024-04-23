package com.skyapi.weatherforecast.location;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;

import ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble;

//GREAT!!
@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTests {
	
	private static final String END_POINT_PATH ="/v1/locations"; 
	
	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper mapper ;
	@MockBean LocationService service;
	
	
	@Test
	public void testAddShouldReturn400BadRequest() throws Exception
	{
		//INVALID LOCATION - WITH NO FIELDS
		Location location = new Location() ;
		
		String bodyContent = this.mapper.writeValueAsString(location);
		
		this.mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
			.andExpect(status().isBadRequest())
			.andDo(print());
			
	}
	
	//USE MOCKITO IN THIS MEHTOD - SINCE GOES THORW THE SERVICE WHICH I NEED OT MOCK
	//AND THE @Service is not loaded on Test WebMvc!
	@Test
	void testAddShouldReturn201Created() throws Exception
	{
		
		Location location = new Location();
		location.setCode("PS_FR");
		location.setCityName("New York City");
		//location.setRegionName("Dan");
		location.setCountryCode("FR");
		location.setCountryName("France");
		location.setEnabled(true);
		location.setTrashed(true);
		
		//CONFIGURE THE MOCK
		Mockito.when(service.add(location)).thenReturn(location); 
		
		String bodyContent = this.mapper.writeValueAsString(location);
		
		this.mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.code", is("PS_FR")))
				.andExpect(jsonPath("$.city_name", is("New York City")))

				.andDo(print());
		
		
	}
	
	
	@Test
	void testListShouldReturn204NoContent() throws Exception 
	{
		//CONFIGURE THE MOCK TO RETURN AN EMPOTY LIST
		Mockito.when(service.list()).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get(END_POINT_PATH))
		.andExpect(status().isNoContent())
		.andDo(print());
		
	}
	
	@Test
	void testListShouldReturn200Ok() throws Exception
	{
		Location location1 = new Location();
		location1.setCode("NYC_USA");
		location1.setCityName("New York City");
		location1.setRegionName("New York");
		location1.setCountryCode("US");
		location1.setCountryName("United States of America");
		location1.setEnabled(true);
		
		Location location2 = new Location();
		location2.setCode("LACA_USA");
		location2.setCityName("Los Angeles");
		location2.setRegionName("California");
		location2.setCountryCode("US");
		location2.setCountryName("United States of America");
		location2.setEnabled(true);
	
		
		//SHOULD NOT BE IN THE RESPONSE BODY - SINCE TRHASHED IS TRUE!
//		Location location3 = new Location();
//		location3.setCode("DELHI_IN");
//		location3.setCityName("New Delhi");
//		location3.setRegionName("Delhi");
//		location3.setCountryCode("IN");
//		location3.setCountryName("Inida");
//		location3.setEnabled(true);
//		location3.setTrashed(true);
		
		//CONFIGURE THE MOCK TO RETURN ONLH THE UN TRASHED LOCATIONS!
		Mockito.when(service.list()).thenReturn(List.of(location1, location2));
		
		
		this.mockMvc.perform(get(END_POINT_PATH))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			//VERIFY THE FIRST OBJECT IN THE ARRAY IN THE RESPONSE 
			.andExpect(jsonPath("$[0].code", is("NYC_USA")))
			.andExpect(jsonPath("$[0].city_name", is("New York City")))
			.andExpect(jsonPath("$[1].code", is("LACA_USA")))
			.andExpect(jsonPath("$[1].city_name", is("Los Angeles")))
			.andDo(print());

	}
	
	//OK 
	//NO MOCK ON THE SERVICE!! request should not go through the controller!
	@Test
	void testGetShouldReturn405MethodNotAllowed() throws Exception
	{
		String requestURI = END_POINT_PATH + "/ABCDE";  
		
		mockMvc.perform(post(requestURI))
		.andExpect(status().isMethodNotAllowed())
		.andDo(print());
		
		
		//CONFIGURE THE MOCK - TO RETURN NULL 
		//Mockito.when(service.get(code)).thenReturn();

	}
	
	
	//OK
	@Test
	void testGetShouldReturn404NotFound() throws Exception
	{
		String requestURI = END_POINT_PATH + "/ABCDE";  
		
		mockMvc.perform(get(requestURI))
		.andExpect(status().isNotFound())
		.andDo(print());
		
		
		//CONFIGURE THE MOCK - TO RETURN NULL 
		//Mockito.when(service.get(code)).thenReturn();

	}
	
	
	@Test
	void testGetShouldReturn200Ok() throws Exception
	{
		String code = "NYC_US";
		String requestURI = END_POINT_PATH + "/" + code;
		
		Location location = new Location();
		location.setCode("NYC_US");
		location.setCityName("New York City");
		location.setRegionName("New York");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		
		
		//CONFIGURE THE MOCK - TO RETURN NULL 
		Mockito.when(service.get(code)).thenReturn(location);
		
			this.mockMvc.perform(get(requestURI))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					//VERIFY THE FIRST OBJECT IN THE ARRAY IN THE RESPONSE 
					.andExpect(jsonPath("$.code", is(code)))
					.andExpect(jsonPath("$.city_name", is("New York City")))
					.andDo(print());
			
	}
	
	

	
	

}
