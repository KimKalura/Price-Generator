package com.spring.priceGenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Discount {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonBackReference(value ="product-discount")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonBackReference(value ="country-discount")
    @JoinColumn(name ="country_id")
    private Country country;

    @Column
    private Double discount;

    public Discount(){}

    public Discount(Long id, Product product, Country country) {
        this.id = id;
        this.product = product;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
