package com.andersen.oleg.countries.mapper;

import com.andersen.oleg.countries.dto.CityDto;
import com.andersen.oleg.countries.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDto cityToDto(City city);

    City dtoToCity(CityDto cityDto);
}
