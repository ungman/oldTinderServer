package io.github.ungman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/available", produces = "application/json")
public class CheckAvailableController {
    @GetMapping
    public boolean isAvailable() {
        return true;
    }
}
