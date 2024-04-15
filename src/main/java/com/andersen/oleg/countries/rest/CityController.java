package com.andersen.oleg.countries.rest;

import com.andersen.oleg.countries.dto.CityDto;
import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Page<City> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageRequest = PageRequest.of(page, size);

        return cityService.getAllCities(pageRequest);
    }

    @GetMapping("/list")
    public Set<String> getUniqueCities() {
        return cityService.getUniqueCityNames();
    }

    @GetMapping("/filter")
    public List<City> getAllByCountryName(@RequestParam String countryName) {
        return cityService.getAllByCountryName(countryName);
    }

    @GetMapping("/search")
    public List<City> searchByCityName(@RequestParam String cityName) {
        return cityService.searchCityByName(cityName);
    }

    @PatchMapping(value = "edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestParam String city, @RequestPart("logo") MultipartFile file) {
        cityService.updateCity(id, city, file);
        return new ResponseEntity<>("City edited", HttpStatus.ACCEPTED);
    }
}
