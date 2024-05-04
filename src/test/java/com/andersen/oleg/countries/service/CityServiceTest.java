package com.andersen.oleg.countries.service;

import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.entity.Country;
import com.andersen.oleg.countries.exception.CityNotFoundException;
import com.andersen.oleg.countries.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CityServiceTest {

    public static final Long CITY_ID = 1L;

    @Mock
    private CityRepository repository;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllCitiesThenReturnPageOfCities() {
        Pageable pageable = mock(Pageable.class);
        Page<City> expectedPage = new PageImpl<>(getExpectedCityList());

        when(repository.findAll(pageable)).thenReturn(expectedPage);

        Page<City> result = cityService.getAllCities(pageable);

        assertEquals(expectedPage, result);
    }

    @Test
    void whenGetUniqueCityNamesThenReturnUniqueCities() {
        Set<String> expectedCitySet = Set.of("New York", "Los Angeles");

        when(repository.getUniqueCityNames()).thenReturn(expectedCitySet);

        Set<String> result = cityService.getUniqueCityNames();

        assertEquals(expectedCitySet, result);
    }

    @Test
    void whenGetAllByCountryNameThenReturnAllRelatedCities() {
        String countryName = "USA";
        List<City> expectedList = getExpectedCityList();

        when(repository.findAllByCountryNameIgnoreCase(countryName)).thenReturn(expectedList);

        List<City> result = cityService.getAllByCountryName(countryName);

        assertEquals(expectedList, result);
    }

    @Test
    void whenSearchCityByNameThenReturnMatchingCities() {
        String cityName = "New";
        List<City> expectedList = getExpectedCityList();

        when(repository.findByNameStartsWithIgnoreCase(cityName)).thenReturn(expectedList);

        List<City> result = cityService.searchCityByName(cityName);

        assertEquals(expectedList, result);
    }

    @Test
    void whenCityFoundByIdThenUpdateCity() {
        String updatedCity = "Updated City";
        City city = new City();
        city.setId(CITY_ID);
        byte[] logoBytes = "logo bytes".getBytes();
        MultipartFile file = new MockMultipartFile("logo", logoBytes);

        when(repository.findById(CITY_ID)).thenReturn(Optional.of(city));
        when(repository.save(city)).thenReturn(city);

        boolean result = cityService.updateCity(CITY_ID, updatedCity, file);

        assertTrue(result);
        assertEquals(updatedCity, city.getName());
        verify(repository, times(1)).save(city);
    }

    @Test
    void whenCityNotFoundThenThrowCityNotFoundException() {
        String updatedCity = "Updated City";
        MultipartFile file = new MockMultipartFile("logo", new byte[]{});

        when(repository.findById(CITY_ID)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityService.updateCity(CITY_ID, updatedCity, file));
    }

    private List<City> getExpectedCityList() {
        return List.of(new City(CITY_ID, "New York", null, new Country("USA")));
    }
}