package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.BrandDTO;
import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.services.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private BrandService brandService;

    private ModelMapper modelMapper;

    @Autowired
    public BrandController(BrandService brandService, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<BrandDTO>> getAll() {
        List<Brand> brands = this.brandService.getAll();
        List<BrandDTO> brandsDTO = new LinkedList<>();
        brands.forEach(brand -> brandsDTO.add(this.modelMapper.map(brand, BrandDTO.class)));
        return ResponseEntity.ok(brandsDTO);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<BrandDTO> get(@PathVariable(name = "id") int id) {
        Brand brand = this.brandService.get(id);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        } else {
            BrandDTO brandDTO = this.modelMapper.map(brand, BrandDTO.class);
            return ResponseEntity.ok(brandDTO);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody BrandDTO brandDTO) {
        Brand brand = this.modelMapper.map(brandDTO, Brand.class);
        int id = this.brandService.add(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody BrandDTO brandDTO) {
        Brand brand = this.modelMapper.map(brandDTO, Brand.class);
        this.brandService.update(id, brand);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.brandService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
