package com.ppbkaf.application.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Alcohol implements Copiable<Alcohol>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 45)
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Brand brand;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Kind kind;
    @Column(scale = 1)
    private double alcoholicStrength;
    private int volume;

    @Override
    public void copy(Alcohol obj) {
        this.setAlcoholicStrength(obj.getAlcoholicStrength());
        this.setBrand(obj.getBrand());
        this.setKind(obj.getKind());
        this.setName(obj.getName());
        this.setVolume(obj.getVolume());
    }
}
