package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.Kind;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface KindRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Cacheable(value = "kindCache")
    List<Kind> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Kind get(int id);

    @Secured("ROLE_ADMIN")
    int add(Kind obj);

    @Secured("ROLE_ADMIN")
    void update(int id, Kind obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);
}
