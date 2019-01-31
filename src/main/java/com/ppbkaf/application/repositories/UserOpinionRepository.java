package com.ppbkaf.application.repositories;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserOpinionRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    int like(int saleId, int userId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    int dislike(int saleId, int userId);

    List<UserOpinionDTO> getAllByUser(int userId);
}
