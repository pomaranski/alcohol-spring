package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.Brand;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface BrandRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Cacheable(value = "brandCache")
    public List<Brand> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Brand get(int id);

    @Secured("ROLE_ADMIN")
    int add(Brand obj);

    @Secured("ROLE_ADMIN")
    void update(int id, Brand obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);
}
