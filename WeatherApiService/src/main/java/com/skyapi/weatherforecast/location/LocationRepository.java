package com.skyapi.weatherforecast.location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skyapi.weatherforecast.common.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, String> {

}
