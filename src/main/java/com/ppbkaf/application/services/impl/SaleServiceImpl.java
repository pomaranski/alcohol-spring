package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.SaleRepository;
import com.ppbkaf.application.services.SaleService;
import com.ppbkaf.application.services.impl.util.UserDetectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService {

    private SaleRepository saleRepository;

    private UserDetectionService userDetectionService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, UserDetectionService userDetectionService) {
        this.saleRepository = saleRepository;
        this.userDetectionService = userDetectionService;
    }

    @Override
    public List<Sale> getAll() {
        try {
            return this.saleRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all sales", ex);
        }
    }

    @Override
    public Sale get(int id) {
        try {
            return this.saleRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get sale", ex);
        }
    }

    @Override
    public int add(Sale obj) {
        try {
            User user = this.userDetectionService.getUser();
            obj.setUser(user);
            return this.saleRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);
            throw new AddException("Cannot add sale", ex);
        }
    }

    @Override
    public void update(int id, Sale obj) {
        try {
            if (this.userDetectionService.isAdmin() ||
                    this.saleRepository.getOwnerId(id) == this.userDetectionService.getUserId()) {
                User user = this.userDetectionService.getUser();
                obj.setUser(user);
                this.saleRepository.update(id, obj);
            } else {
                log.info("Cannot update id={}, because of privileges", id);
            }
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update sale", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            if (this.userDetectionService.isAdmin() ||
                    this.saleRepository.getOwnerId(id) == this.userDetectionService.getUserId()) {
                this.saleRepository.delete(id);
            } else {
                log.info("Cannot delete id={}, because of privileges", id);
            }
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete sale", ex);
        }
    }

    @Override
    public List<Sale> getAllSalesByShopAndKind(int shopId, int kindId) {
        try {
            return this.saleRepository.getAllSalesByShopAndKind(shopId, kindId);
        } catch (Exception ex) {
            log.info("Cannot get all by shop id={}, kind id={}", shopId, kindId, ex);
            throw new GetException("Cannot get all sales by shop and kind", ex);
        }
    }

    @Override
    public List<Sale> getAllSalesByShop(int shopId) {
        try {
            return this.saleRepository.getAllSalesByShop(shopId);
        } catch (Exception ex) {
            log.info("Cannot get all by shop id={}", shopId, ex);
            throw new GetException("Cannot get all sales by shop", ex);
        }
    }

    @Override
    public List<Sale> getAllSalesByKind(int kindId) {
        try {
            return this.saleRepository.getAllSalesByKind(kindId);
        } catch (Exception ex) {
            log.info("Cannot get all by kind id={}", kindId, ex);
            throw new GetException("Cannot get all sales by kind", ex);
        }
    }

    @Override
    public List<Sale> getAllSalesByUser() {
        try {
            int userId = this.userDetectionService.getUserId();
            return this.saleRepository.getAllSalesByUser(userId);
        } catch (Exception ex) {
            log.info("Cannot get all by user", ex);
            throw new GetException("Cannot get all sales by user", ex);
        }
    }
}
