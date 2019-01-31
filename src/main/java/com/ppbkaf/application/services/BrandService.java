package com.ppbkaf.application.services;

import com.ppbkaf.application.entities.Brand;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface BrandService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Brand> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Brand get(int id);

    @Secured("ROLE_ADMIN")
    int add(Brand obj);

    @Secured("ROLE_ADMIN")
    void update(int id, Brand obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);
}
