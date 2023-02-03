package com.spring.priceGenerator.controller;

import com.spring.priceGenerator.model.Country;
import com.spring.priceGenerator.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {
    private CountryService countryService;

    @Autowired

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }
}
