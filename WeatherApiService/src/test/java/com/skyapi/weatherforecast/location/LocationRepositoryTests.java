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
	
	
////////////////////////////
//1 CREATE LOCATION API  - DONE

	//GREAT!
	@Disabled
	@DisplayName("create location success")
	@Test
	public void testAddSuccess() {
		Location location = new Location();
		location.setCode("NYC_US");
		location.setCityName("New York City");
		//location.setRegionName("Dan");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		location.setTrashed(true);
		
		Location savedLocation = repository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("NYC_US");
	}
	
////////////////////////////
//2. List Locations API  -DONE

	
	//@Disabled
	@DisplayName("List All Locations")
	@Test
	void testListAllLocations()
	{
		List<Location> locations = (List<Location>)this.repository.findAll();
		
		assertThat(locations.size()).isEqualTo(2); 
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
	
	
	////////////////////////////
	//3. FIND LOCATION API 
	
	//OK!  CHECK BOTH FIELDS! OK!
	//		l1_0.trashed=0 
    //		and l1_0.code=?
	@Test
	void testGetNotFound()
	{
		String code = "ABCD"; 
		Location location = repository.findByCode(code);
		
		assertThat(location).isNull();
		
		System.out.println(location);
		
	}
	
	//OK!
	@Test
	void testGetFound()
	{
		String code = "NYC_US"; 
		Location location = repository.findByCode(code);
		
		assertThat(location).isNotNull();
		
		System.out.println(location);
	}
	
	@Test
	void testGetFoundWhenTrashedStatusIsTrueShouldReturnNull()
	{
		String code = "PS_FR"; 
		Location location = repository.findByCode(code);
		
		assertThat(location).isNull();
		
		System.out.println(location);
	}
	
	////////////////
	//DELET API 
	
	@Test
	void testTrashByCodeSuccess()
	{
		String code = "NYC_USA"; 
		
		//WHEN
		this.repository.trashByCode(code);
		
		Location deletedLocation = this.repository.findByCode(code);
		
		assertThat(deletedLocation).isNull();
		
		System.out.println(deletedLocation); 
	}

}
