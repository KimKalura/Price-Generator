package com.spring.priceGenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Quotation {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime expireDate;

    @Column
    private Double ageDiscount;

    @Column
    private Double countryDiscount;

    @ManyToOne
    @JsonBackReference(value = "user-quotation")
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JsonBackReference(value = "product-quotation")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonBackReference(value = "order-quotation")
    @JoinColumn(name = "order_id")
    private Order order;

    public Quotation(){}


    public Quotation(Long id, LocalDateTime expireDate, Double ageDiscount, Double countryDiscount, User user, Product product, Order order) {
        this.id = id;
        this.expireDate = expireDate;
        this.ageDiscount = ageDiscount;
        this.countryDiscount = countryDiscount;
        this.user = user;
        this.product = product;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Double getAgeDiscount() {
        return ageDiscount;
    }

    public void setAgeDiscount(Double ageDiscount) {
        this.ageDiscount = ageDiscount;
    }

    public Double getCountryDiscount() {
        return countryDiscount;
    }

    public void setCountryDiscount(Double countryDiscount) {
        this.countryDiscount = countryDiscount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
