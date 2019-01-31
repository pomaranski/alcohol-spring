package com.ppbkaf.application.services;

import com.ppbkaf.application.entities.Kind;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface KindService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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
