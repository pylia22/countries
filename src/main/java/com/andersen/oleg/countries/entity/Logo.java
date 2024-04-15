package com.andersen.oleg.countries.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Logo implements Serializable {

    @Id
    @Column(name = "city_id")
    private Long id;

    //@Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    private byte[] logoData;

    @OneToOne
    @MapsId
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City city;
}
