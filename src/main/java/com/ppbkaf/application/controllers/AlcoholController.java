package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.AlcoholDTO;
import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.services.AlcoholService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/alcohols")
public class AlcoholController {

    private AlcoholService alcoholService;

    private ModelMapper modelMapper;

    @Autowired
    public AlcoholController(AlcoholService alcoholService, ModelMapper modelMapper) {
        this.alcoholService = alcoholService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<AlcoholDTO>> getAll() {
        List<Alcohol> alcohols = this.alcoholService.getAll();
        return this.getList(alcohols);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<AlcoholDTO> get(@PathVariable(name = "id") int id) {
        Alcohol alcohol = this.alcoholService.get(id);
        if (alcohol == null) {
            return ResponseEntity.notFound().build();
        } else {
            AlcoholDTO alcoholDTO = this.modelMapper.map(alcohol, AlcoholDTO.class);
            return ResponseEntity.ok(alcoholDTO);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody AlcoholDTO alcoholDTO) {
        Alcohol alcohol = this.modelMapper.map(alcoholDTO, Alcohol.class);
        int id = this.alcoholService.add(alcohol);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody AlcoholDTO alcoholDTO) {
        Alcohol alcohol = this.modelMapper.map(alcoholDTO, Alcohol.class);
        this.alcoholService.update(id, alcohol);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.alcoholService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(
            value = "/kind/{kindId}",
            produces = "application/json")
    public ResponseEntity<List<AlcoholDTO>> getAllAlcoholsByKind(@PathVariable(name = "kindId") int kindId) {
        List<Alcohol> alcohols = this.alcoholService.getAllAlcoholsByKind(kindId);
        return this.getList(alcohols);
    }

    private ResponseEntity<List<AlcoholDTO>> getList(List<Alcohol> alcohols) {
        if (alcohols == null || alcohols.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<AlcoholDTO> alcoholsDTO = new LinkedList<>();
            alcohols.forEach(alcohol -> alcoholsDTO.add(this.modelMapper.map(alcohol, AlcoholDTO.class)));
            return ResponseEntity.ok(alcoholsDTO);
        }
    }
}
