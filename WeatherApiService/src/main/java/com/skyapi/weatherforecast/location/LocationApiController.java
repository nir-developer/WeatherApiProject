package com.skyapi.weatherforecast.location;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.skyapi.weatherforecast.common.Location;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {
	
	private final LocationService service;

	public LocationApiController(LocationService service) {
		
		this.service = service;
	} 
	//
	
	@PostMapping
	public ResponseEntity<Location> createLocation(@RequestBody @Valid Location location)
	{
		//VALIDATION BY THE SPRINGBOOT VALIDATION - LATER
		Location addedLocation = this.service.add(location);
		
		//CREATE THE URI LOCATION FOR THE NEW CRETED RESOURCE AS IN SWAGGER
		URI uri = URI.create("/v1/locations/" + addedLocation.getCode());
		
		ResponseEntity response = ResponseEntity.created(uri).body(addedLocation);
		
		return response;
		
	}
	
	/**
	 * - based on the docs  :If the service returns [] - then the Api controller needs to return 204 status - no content
	 * - ? for any type
	 */
	
	@GetMapping
	public ResponseEntity<?> listLocations()
	{
		List<Location> locations =  this.service.list();
		
		if(locations.isEmpty()) return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(locations);
	}
	
	
	@GetMapping("/{code}")
	public ResponseEntity<?> getLocation(@PathVariable("code") String code)
	{
		
		Location location = this.service.get(code);
		
		//DONT THROW!nothing is thrown..aslo not in the service
		if(location == null)
		{
			return ResponseEntity.notFound().build(); 
		}
		
		//found!
		return ResponseEntity.ok(location);
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location)
	{
		//TRY - CATCH SINCE THE SERVICE MAY THROW 
		try 
		{
			Location updatedLocation = this.service.update(location);
			
			//SUCCESS IF REACHED HERE -THEN RETURN  THE RESPOSNE WITH THE UPDATED LOCATION IN THE BODY
			return ResponseEntity.ok(updatedLocation);
		}
		catch(Exception ex)
		{
			return ResponseEntity.notFound().build(); 
		}
	}
	
	//WHY GET???
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteLocation(@PathVariable("code") String code)
	{
		try
		{
			this.service.delete(code);
			
			return ResponseEntity.noContent().build();
			
		}
		catch(LocationNotFoundException exc)
		{
			return ResponseEntity.notFound().build(); 

		}
	}

}
