package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.ShopDTO;
import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.services.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private ShopService shopService;

    private ModelMapper modelMapper;

    @Autowired
    public ShopController(ShopService shopService, ModelMapper modelMapper) {
        this.shopService = shopService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<ShopDTO>> getAll() {
        List<Shop> shops = this.shopService.getAll();
        List<ShopDTO> shopsDTO = new LinkedList<>();
        shops.forEach(shop -> shopsDTO.add(this.modelMapper.map(shop, ShopDTO.class)));
        return ResponseEntity.ok(shopsDTO);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<ShopDTO> get(@PathVariable(name = "id") int id) {
        Shop shop = this.shopService.get(id);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        } else {
            ShopDTO shopDTO = this.modelMapper.map(shop, ShopDTO.class);
            return ResponseEntity.ok(shopDTO);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody ShopDTO shopDTO) {
        Shop shop = this.modelMapper.map(shopDTO, Shop.class);
        int id = this.shopService.add(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody ShopDTO shopDTO) {
        Shop shop = this.modelMapper.map(shopDTO, Shop.class);
        this.shopService.update(id, shop);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.shopService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
