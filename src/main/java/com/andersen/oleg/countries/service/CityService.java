package com.andersen.oleg.countries.service;

import com.andersen.oleg.countries.config.utils.LogoUtils;
import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.entity.Logo;
import com.andersen.oleg.countries.repository.CityRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class CityService {

    private final CityRepository repository;
    private final LogoService logoService;

    public CityService(CityRepository repository, LogoService logoService) {
        this.repository = repository;
        this.logoService = logoService;
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
    @Transactional
    public void updateCity(Long id, String updatedCity, MultipartFile file) {
        City city = repository.findById(id).orElseThrow(() -> new RuntimeException("not found city"));
        Logo logo = logoService.saveLogo(id, file);

        city.setName(updatedCity);
        city.setLogo(logo);

        repository.save(city);
    }
}
