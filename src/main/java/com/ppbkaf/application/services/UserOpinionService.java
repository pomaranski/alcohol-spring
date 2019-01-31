package com.ppbkaf.application.services;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserOpinionService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    void like(int saleId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    void dislike(int saleId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<UserOpinionDTO> getAllByUser();
}
