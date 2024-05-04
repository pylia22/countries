package com.andersen.oleg.countries.service;

import com.andersen.oleg.countries.config.utils.LogoUtils;
import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.exception.CityNotFoundException;
import com.andersen.oleg.countries.repository.CityRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public Page<City> getAllCities(Pageable pageRequest) {
        return repository.findAll(pageRequest);
    }

    public Set<String> getUniqueCityNames() {
        return repository.getUniqueCityNames();
    }

    public List<City> getAllByCountryName(String countryName) {
        return repository.findAllByCountryNameIgnoreCase(countryName);
    }

    public List<City> searchCityByName(String cityName) {
        return repository.findByNameStartsWithIgnoreCase(cityName);
    }

    @SneakyThrows
    public boolean updateCity(Long id, String updatedCity, MultipartFile file) {
        Optional<City> cityOptional = repository.findById(id);

        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException(id);
        }
        City city = cityOptional.get();
        city.setName(updatedCity);

        if (file != null && !file.isEmpty()) {
            city.setLogo(LogoUtils.compressImage(file.getBytes()));
        }
        repository.save(city);
        return true;
    }
}
