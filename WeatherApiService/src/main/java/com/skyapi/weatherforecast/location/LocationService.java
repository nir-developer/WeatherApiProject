package com.skyapi.weatherforecast.location;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skyapi.weatherforecast.common.Location;

import jakarta.transaction.Transactional;

@Service
//CHAD - CHECK:
@Transactional
public class LocationService {

	private LocationRepository repository;

	public LocationService(LocationRepository repository) {
		super();
		this.repository = repository;
	}
	
	
	public Location add(Location location)
	{
		return this.repository.save(location);
	}
	
	public List<Location> list()
	{
		return this.repository.findUntrashed();
	}
	
	public Location get(String code)
	{
		return this.repository.findByCode(code);
	}
	
	public Location update(Location location) throws LocationNotFoundException
	{
		String code = location.getCode(); 
		
		Location locationInDB = this.repository.findByCode(code);
		
		if(locationInDB == null)
		{
			throw new LocationNotFoundException("No location found with the given code: " + code);
		}
		
		//Update  - all fields other than trashed and code 
		locationInDB.setCityName(location.getCityName());
		locationInDB.setRegionName(location.getRegionName());
		locationInDB.setCountryName(location.getCountryName());
		locationInDB.setCountryCode(location.getCountryCode());
		locationInDB.setEnabled(location.isEnabled());
		
		
		return this.repository.save(location);
		
	}
	
}
