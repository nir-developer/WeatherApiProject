package com.skyapi.weatherforecast.location;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skyapi.weatherforecast.common.Location;

import jakarta.transaction.Transactional;

@Repository
//MANDATORY FOR NON IN MEMORY H2!
@Transactional
public interface LocationRepository extends CrudRepository<Location, String> {
	
	//JPQL -> ADVANTAGE! IF SYNTAX ERROR THAN THE APP WILL BE CATCHED ON STARTUP !
	@Query("SELECT l FROM Location l WHERE l.trashed=false")
	List<Location> findUntrashed();
	
	
	@Query("SELECT l FROM Location l WHERE l.trashed=false AND l.code= ?1")
	public Location findByCode(String code);

	//NOTE @MODIFYING
	//Location are not deleted -> can not use the deleteByCode of JPA! 
	@Modifying
	@Query("UPDATE Location SET trashed = true WHERE code = ?1")
	public void trashByCode(String code);
}


