package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.SaleDTO;
import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.services.SaleService;
import com.ppbkaf.application.services.UserOpinionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private SaleService saleService;

    private UserOpinionService userOpinionService;

    private ModelMapper modelMapper;

    @Autowired
    public SaleController(SaleService saleService, UserOpinionService userOpinionService, ModelMapper modelMapper) {
        this.saleService = saleService;
        this.userOpinionService = userOpinionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<SaleDTO>> getAll() {
        List<Sale> sales = this.saleService.getAll();
        return this.getList(sales);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<SaleDTO> get(@PathVariable(name = "id") int id) {
        Sale sale = this.saleService.get(id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        } else {
            SaleDTO saleDTO = this.modelMapper.map(sale, SaleDTO.class);
            return ResponseEntity.ok(saleDTO);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody Sale sale) {
        int id = this.saleService.add(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody SaleDTO saleDTO) {
        Sale sale = this.modelMapper.map(saleDTO, Sale.class);
        this.saleService.update(id, sale);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.saleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(
            value = "/{saleId}/like",
            produces = "application/json")
    public ResponseEntity<?> like(@PathVariable(name = "saleId") int id) {
        this.userOpinionService.like(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(
            value = "/{saleId}/dislike",
            produces = "application/json")
    public ResponseEntity<?> dislike(@PathVariable(name = "saleId") int saleId) {
        this.userOpinionService.dislike(saleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(
            value = "/shop/{shopId}/kind/{kindId}",
            produces = "application/json")
    public ResponseEntity<List<SaleDTO>> getAllSalesByShopAndKind(@PathVariable(name = "shopId") int shopId,
                                                                  @PathVariable(name = "kindId") int kindId) {
        List<Sale> sales = this.saleService.getAllSalesByShopAndKind(shopId, kindId);
        return this.getList(sales);
    }

    @GetMapping(
            value = "/shop/{shopId}",
            produces = "application/json")
    public ResponseEntity<List<SaleDTO>> getAllSalesByShop(@PathVariable(name = "shopId") int shopId) {
        List<Sale> sales = this.saleService.getAllSalesByShop(shopId);
        return this.getList(sales);
    }

    @GetMapping(
            value = "/kind/{kindId}",
            produces = "application/json")
    public ResponseEntity<List<SaleDTO>> getAllSalesByKind(@PathVariable(name = "kindId") int kindId) {
        List<Sale> sales = this.saleService.getAllSalesByKind(kindId);
        return this.getList(sales);
    }

    @GetMapping(
            value = "/user/",
            produces = "application/json")
    public ResponseEntity<List<SaleDTO>> getAllSalesByUser() {
        List<Sale> sales = this.saleService.getAllSalesByUser();
        return this.getList(sales);
    }

    private ResponseEntity<List<SaleDTO>> getList(List<Sale> sales) {
        List<SaleDTO> salesDTO = new LinkedList<>();
        sales.forEach(sale -> salesDTO.add(this.modelMapper.map(sale, SaleDTO.class)));
        return ResponseEntity.ok(salesDTO);
    }
}
