package com.andersen.oleg.countries.repository;

import com.andersen.oleg.countries.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select distinct c.name from City c")
    Set<String> getUniqueCityNames();

    List<City> findAllByCountryNameIgnoreCase(String countryName);

    List<City> findByNameStartsWithIgnoreCase(String cityName);
}
