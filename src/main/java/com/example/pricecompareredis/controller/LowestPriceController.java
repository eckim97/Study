package com.example.pricecompareredis.controller;

import com.example.pricecompareredis.service.LowestPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/")
public class LowestPriceController {

    @Autowired
    private LowestPriceService mlps;

    @GetMapping("/getZSETValue")
    public Set GetZsetValue(String key) {
        return mlps.getZsetValue(key);
    }
}
