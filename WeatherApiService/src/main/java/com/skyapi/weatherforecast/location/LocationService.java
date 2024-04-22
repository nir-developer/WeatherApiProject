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
	
}
