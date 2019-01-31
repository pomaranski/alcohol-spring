package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.BrandRepository;
import com.ppbkaf.application.services.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAll() {
        try {
            return brandRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all brands", ex);
        }
    }

    @Override
    public Brand get(int id) {
        try {
            return brandRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get brand", ex);
        }
    }

    @Override
    public int add(Brand obj) {
        try {
            return brandRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);
            throw new AddException("Cannot add brand", ex);
        }
    }

    @Override
    public void update(int id, Brand obj) {
        try {
            brandRepository.update(id, obj);
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update brand", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            brandRepository.delete(id);
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete brand", ex);
        }
    }
}
