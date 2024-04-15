package com.andersen.oleg.countries.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "city", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Logo logo;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public void setLogo(Logo logo) {
        this.logo = logo;
        this.logo.setCity(this);
    }

}
