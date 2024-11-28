package com.github.bat333.stockroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/part")
public class controllerPart {


    @GetMapping
    public String hello(){
        return "HELLOU";
    }
}
