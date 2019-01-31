package com.ppbkaf.application.controllers;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import com.ppbkaf.application.services.UserOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opinions")
public class UserOpinionController {

    private UserOpinionService userOpinionService;

    @Autowired
    public UserOpinionController(UserOpinionService userOpinionService) {
        this.userOpinionService = userOpinionService;
    }

    @GetMapping(
            value = "",
            produces = "application/json")
    public ResponseEntity<Map<Integer, UserOpinionDTO>> getAll() {
        List<UserOpinionDTO> userOpinionDTOS = this.userOpinionService.getAllByUser();
        Map<Integer, UserOpinionDTO> userOpinionDTOMap = userOpinionDTOS.stream()
                .collect(Collectors.toMap(UserOpinionDTO::getSaleId, opinion -> opinion));
        return ResponseEntity.ok(userOpinionDTOMap);

    }
}
