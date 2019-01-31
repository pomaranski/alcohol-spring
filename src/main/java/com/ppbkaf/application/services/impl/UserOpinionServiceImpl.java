package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.repositories.UserOpinionRepository;
import com.ppbkaf.application.services.UserOpinionService;
import com.ppbkaf.application.services.impl.util.UserDetectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserOpinionServiceImpl implements UserOpinionService {

    private UserOpinionRepository userOpinionRepository;

    private UserDetectionService userDetectionService;

    @Autowired
    public UserOpinionServiceImpl(UserOpinionRepository userOpinionRepository, UserDetectionService userDetectionService) {
        this.userOpinionRepository = userOpinionRepository;
        this.userDetectionService = userDetectionService;
    }

    @Override
    public void like(int saleId) {
        try {
            int userId = this.userDetectionService.getUserId();
            this.userOpinionRepository.like(saleId, userId);
        } catch (Exception ex) {
            log.error("Cannot like saleId={} userId={}", saleId, ex);
            throw new AddException("Cannot like", ex);
        }
    }

    @Override
    public void dislike(int saleId) {
        try {
            int userId = this.userDetectionService.getUserId();
            this.userOpinionRepository.dislike(saleId, userId);
        } catch (Exception ex) {
            log.error("Cannot like saleId={} userId={}", saleId, ex);
            throw new AddException("Cannot like", ex);
        }
    }

    @Override
    public List<UserOpinionDTO> getAllByUser() {
        try {
            int userId = this.userDetectionService.getUserId();
            return this.userOpinionRepository.getAllByUser(userId);
        } catch (Exception ex) {
            log.error("Cannot like", ex);
            throw new GetException("Cannot getAll", ex);
        }
    }
}
