package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.ShopRepository;
import com.ppbkaf.application.services.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public List<Shop> getAll() {
        try {
            return shopRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all shops", ex);
        }
    }

    @Override
    public Shop get(int id) {
        try {
            return shopRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get shop", ex);
        }
    }

    @Override
    public int add(Shop obj) {
        try {
            return shopRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);
            throw new AddException("Cannot add shop", ex);
        }
    }

    @Override
    public void update(int id, Shop obj) {
        try {
            shopRepository.update(id, obj);
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update shop", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            shopRepository.delete(id);
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete shop", ex);
        }
    }
}
