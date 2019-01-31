package com.ppbkaf.application.controllers;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<User> get(@PathVariable(name = "id") int id) {
        User user = this.userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Integer> add(@Valid @RequestBody User user) {
        int id = this.userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Valid @RequestBody User user) {
        this.userService.update(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
