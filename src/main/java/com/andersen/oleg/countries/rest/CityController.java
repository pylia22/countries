package com.andersen.oleg.countries.rest;

import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cities")
@Slf4j
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @Operation(summary = "get all cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities"),
            @ApiResponse(responseCode = "204", description = "No cities found")})
    public ResponseEntity<Page<City>> getCitiesPage(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cityPage = cityService.getAllCities(pageable);

        if (cityPage.hasContent()) {
            return ResponseEntity.ok(cityPage);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/list")
    @Operation(summary = "list all unique city names")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved unique cities")
    public ResponseEntity<Set<String>> getUniqueCities() {
        Set<String> uniqueCities = cityService.getUniqueCityNames();
        log.info("Retrieving unique cities list");

        return ResponseEntity.ok(uniqueCities);
    }

    @GetMapping("/filter")
    @Operation(summary = "get all cities by country name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities for specified country"),
            @ApiResponse(responseCode = "404", description = "No cities found for specified country")})
    public ResponseEntity<List<City>> getAllByCountryName(@Parameter(name = "country name", example = "Germany")
                                                          @RequestParam String countryName) {
        log.info("Getting all cities for country: {}", countryName);
        List<City> cities = cityService.getAllByCountryName(countryName);

        return cities.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cities);
    }

    @GetMapping("/search")
    @Operation(summary = "search by city name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities for specified name"),
            @ApiResponse(responseCode = "404", description = "No cities found for specified name")})
    public ResponseEntity<List<City>> searchByCityName(@Parameter(name = "city name", example = "London")
                                                       @RequestParam String cityName) {
        log.info("Searching for cities with name: {}", cityName);
        List<City> cities = cityService.searchCityByName(cityName);

        if (cities.isEmpty()) {
            log.info("No cities found with name: {}", cityName);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    @PatchMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @Operation(summary = "edit city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "City has been successfully updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden to access"),
            @ApiResponse(responseCode = "500", description = "Failed to update the city")})
    public ResponseEntity<String> editCity(@PathVariable @Parameter(name = "city id", example = "1") Long id,
                                           @RequestParam @Parameter(name = "city name", example = "Madrid") String city,
                                           @RequestPart("logo") @Parameter(name = "city logo") MultipartFile file) {
        boolean isUpdated = cityService.updateCity(id, city, file);

        if (isUpdated) {
            log.info("City with id: {} has been edited successfully.", id);
            return new ResponseEntity<>("City was successfully updated", HttpStatus.ACCEPTED);
        } else {
            log.error("Failed to edit city with id: {}.", id);
            return new ResponseEntity<>("Failed to update city", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

