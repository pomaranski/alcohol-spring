package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.Shop;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ShopRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Shop> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Shop get(int id);

    @Secured("ROLE_ADMIN")
    int add(Shop obj);

    @Secured("ROLE_ADMIN")
    void update(int id, Shop obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);
}
