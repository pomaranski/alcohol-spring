package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.UserDTO;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {

    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public AccountController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(
            value = "/login",
            produces = "application/json")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = "/register",
            produces = "application/json")
    public ResponseEntity<Integer> register(@Valid @RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        int id = this.userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping(
            value = "/activate",
            produces = "application/json")
    public ResponseEntity<?> activate(@RequestParam("token") String token) {
        this.userService.activate(token);
        return ResponseEntity.ok().build();
    }
}
