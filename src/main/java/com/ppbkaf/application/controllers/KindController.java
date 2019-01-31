package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.KindDTO;
import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.services.KindService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/kinds")
public class KindController {

    private KindService kindService;

    private ModelMapper modelMapper;

    @Autowired
    public KindController(KindService kindService, ModelMapper modelMapper) {
        this.kindService = kindService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<KindDTO>> getAll() {
        List<Kind> kinds = this.kindService.getAll();
        List<KindDTO> kindsDTO = new LinkedList<>();
        kinds.forEach(kind -> kindsDTO.add(this.modelMapper.map(kind, KindDTO.class)));
        return ResponseEntity.ok(kindsDTO);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<KindDTO> get(@PathVariable(name = "id") int id) {
        Kind kind = this.kindService.get(id);
        if (kind == null) {
            return ResponseEntity.notFound().build();
        } else {
            KindDTO kindDTO = this.modelMapper.map(kind, KindDTO.class);
            return ResponseEntity.ok(kindDTO);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody KindDTO kindDTO) {
        Kind kind = this.modelMapper.map(kindDTO, Kind.class);
        int id = this.kindService.add(kind);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody Kind kind) {
        this.kindService.update(id, kind);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.kindService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
