package com.spring.priceGenerator.service;

import com.spring.priceGenerator.model.Country;
import com.spring.priceGenerator.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryService {

    CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }



    public Country addCountry(Country country) {
        Country foundCountry = countryRepository.findCountryByCountryName(country.getCountryName());
        if (foundCountry == null) {
            return countryRepository.save(country);
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "country already exists");
        }
    }
}
