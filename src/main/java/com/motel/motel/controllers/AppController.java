package com.motel.motel.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Value("${appName}")
    private String appName;

    @GetMapping({"/", ""})
    public ResponseEntity<?> appName(){
        return ResponseEntity.ok(appName);
    }

    @Data
    static class ValueDTO{
        private String value = "value";
        private int id = 1;
    }
    @GetMapping("/value")
    public ValueDTO value(){
        return new ValueDTO();
    }

}
