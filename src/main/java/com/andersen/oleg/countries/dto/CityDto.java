package com.andersen.oleg.countries.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CityDto {

    private Long id;
    private String city;
    private String country;
    private MultipartFile logo;
}
