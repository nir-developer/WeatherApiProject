package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

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
		location.setCode("MBMH_IN");
		location.setCityName("Mumbai");
		location.setRegionName("Maharashtra");
		location.setCountryCode("IN");
		location.setCountryName("India");
		location.setEnabled(true);
		
		Location savedLocation = repository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("MBMH_IN");
	}

}
