package com.spring.priceGenerator.repository;

import com.spring.priceGenerator.model.Discount;
import com.spring.priceGenerator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findDiscountByCountry_CountryNameAndProduct(String countryName, Product product);
}