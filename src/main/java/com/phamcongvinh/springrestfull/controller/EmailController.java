package com.phamcongvinh.springrestfull.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phamcongvinh.springrestfull.service.EmailService;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @GetMapping("/email")
   
    public String sendSimpleEmail() {
        this.emailService.sendSimpleEmail();
        return "ok";
    }
    
}
