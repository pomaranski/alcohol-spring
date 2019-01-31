package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.Alcohol;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlcoholRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Cacheable(value = "alcoholCache")
    List<Alcohol> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Alcohol get(int id);

    @Secured("ROLE_ADMIN")
    int add(Alcohol obj);

    @Secured("ROLE_ADMIN")
    void update(int id, Alcohol obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Alcohol> getAllAlcoholsByKind(int kindId);
}
