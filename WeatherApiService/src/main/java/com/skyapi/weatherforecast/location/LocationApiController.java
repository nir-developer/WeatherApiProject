package com.skyapi.weatherforecast.location;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyapi.weatherforecast.common.Location;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {
	
	private final LocationService service;

	public LocationApiController(LocationService service) {
		
		this.service = service;
	} 
	
	
	@PostMapping
	public ResponseEntity<Location> createLocation(@RequestBody Location location)
	{
		//VALIDATION BY THE SPRINGBOOT VALIDATION - LATER
		Location addedLocation = this.service.add(location);
		
		//CREATE THE URI LOCATION FOR THE NEW CRETED RESOURCE AS IN SWAGGER
		URI uri = URI.create("/v1/locations/" + addedLocation.getCode());
		
		ResponseEntity response = ResponseEntity.created(uri).body(addedLocation);
		
		return response;
		
	}
	
	
	

}
