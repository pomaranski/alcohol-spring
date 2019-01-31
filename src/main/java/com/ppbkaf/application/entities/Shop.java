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
public class Shop implements Copiable<Shop>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 45)
    private String name;

    @Override
    public void copy(Shop obj) {
        this.setName(obj.getName());
    }
}
