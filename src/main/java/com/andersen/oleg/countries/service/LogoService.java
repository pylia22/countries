package com.andersen.oleg.countries.service;

import com.andersen.oleg.countries.config.utils.LogoUtils;
import com.andersen.oleg.countries.entity.Logo;
import com.andersen.oleg.countries.repository.LogoRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LogoService {

    private final LogoRepository repository;

    public LogoService(LogoRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    public Logo saveLogo(Long id, MultipartFile logoFile) {
        Logo logo = Logo.builder()
                .name(logoFile.getOriginalFilename())
                .logoData(LogoUtils.compressImage(logoFile.getBytes()))
                .build();
        repository.save(logo);
        return logo;
    }
}
