package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.AlcoholRepository;
import com.ppbkaf.application.services.AlcoholService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlcoholServiceImpl implements AlcoholService {

    private AlcoholRepository alcoholRepository;

    @Autowired
    public AlcoholServiceImpl(AlcoholRepository alcoholRepository) {
        this.alcoholRepository = alcoholRepository;
    }

    @Override
    public List<Alcohol> getAll() {
        try {
            return alcoholRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all alcohols", ex);
        }
    }

    @Override
    public Alcohol get(int id) {
        try {
            return alcoholRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get alcohol", ex);
        }
    }

    @Override
    public int add(Alcohol obj) {
        try {
            return alcoholRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);
            throw new AddException("Cannot add alcohol", ex);
        }
    }

    @Override
    public void update(int id, Alcohol obj) {
        try {
            alcoholRepository.update(id, obj);
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update alcohol", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            alcoholRepository.delete(id);
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete alcohol", ex);
        }
    }

    @Override
    public List<Alcohol> getAllAlcoholsByKind(int kindId) {
        try {
            return this.alcoholRepository.getAllAlcoholsByKind(kindId);
        } catch (Exception ex) {
            log.info("Cannot get all by kind id={}", kindId, ex);
            throw new GetException("Cannot get all sales by kind", ex);
        }
    }
}
