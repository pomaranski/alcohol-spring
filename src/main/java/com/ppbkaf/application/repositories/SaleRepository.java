package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.Sale;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface SaleRepository {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Sale> getAll();

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Sale get(int id);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    int add(Sale obj);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    void update(int id, Sale obj);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    void delete(int id);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Sale> getAllSalesByShopAndKind(int shopId, int kindId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Sale> getAllSalesByShop(int shopId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Sale> getAllSalesByKind(int kindId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Sale> getAllSalesByUser(int userId);

    int getOwnerId(int id);
}
