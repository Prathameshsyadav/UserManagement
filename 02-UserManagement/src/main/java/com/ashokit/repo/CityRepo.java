package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.CityEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer> {
	
	@Query(value = "select * from CITY_MASTER where state_id=:sId",
			nativeQuery = true)
	public List<CityEntity> findCityByStateId(Integer sId);

}
