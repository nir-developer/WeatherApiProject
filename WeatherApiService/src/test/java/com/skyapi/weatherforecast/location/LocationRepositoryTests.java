package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Console;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.skyapi.weatherforecast.common.Location;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTests {
	
	@Autowired
	private LocationRepository repository;
	
	
	@DisplayName("create location success")
	@Test
	public void testAddSuccess() {
		Location location = new Location();
		location.setCode("PS_FR");
		location.setCityName("New York City");
		//location.setRegionName("Dan");
		location.setCountryCode("FR");
		location.setCountryName("France");
		location.setEnabled(true);
		location.setTrashed(true);
		
		Location savedLocation = repository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("PS_FR");
	}
	
	//@Disabled
	@DisplayName("List All Locations")
	@Test
	void testListAllLocations()
	{
		List<Location> locations = (List<Location>)this.repository.findAll();
		
		assertThat(locations.size()).isEqualTo(4); 
		//locations.forEach(l -> System.out.println(l));
		locations.forEach(l -> System.out.println(l));
	}
	
	@DisplayName("List All Non Trashed Locations")
	@Test
	void testListAllNonTrashedLocations()
	{
		List<Location> nonTrashedLocatoins = this.repository.findUntrashed();
		
		assertThat(nonTrashedLocatoins.size()).isEqualTo(3); 
		
		nonTrashedLocatoins.forEach(l -> System.out.println(l));
	}

}
