package com.ppbkaf.application.controllers;

import com.ppbkaf.application.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    private MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping(
            value = "/resend",
            produces = "application/json")
    public ResponseEntity<?> resend(@Email @RequestParam("email") String email) {
        this.mailService.resend(email);
        return ResponseEntity.ok().build();
    }
}
