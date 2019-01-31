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
public class Sale implements Copiable<Sale>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Alcohol alcohol;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Shop shop;
    @Column(scale = 2)
    private double price;
    @Embedded
    @Column(nullable = false)
    private Rate rate;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Override
    public void copy(Sale obj) {
        this.setAlcohol(obj.getAlcohol());
        this.setPrice(obj.getPrice());
        this.setRate(obj.getRate());
        this.setShop(obj.getShop());
        this.setUser(obj.getUser());
    }
}
