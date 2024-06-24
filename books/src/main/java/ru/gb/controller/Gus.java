package ru.gb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Gus {

    @GetMapping("/test")
    public String test() {
        return "Javokhir";
    }

}
