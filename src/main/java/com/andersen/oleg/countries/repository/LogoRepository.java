package com.andersen.oleg.countries.repository;

import com.andersen.oleg.countries.entity.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {
}
