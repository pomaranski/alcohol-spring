package com.ppbkaf.application.services;

import com.ppbkaf.application.entities.Alcohol;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface AlcoholService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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
