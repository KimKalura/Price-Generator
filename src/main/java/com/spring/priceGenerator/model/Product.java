package com.spring.priceGenerator.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private ProductType productType;

    @Column
    private Integer productPrice;

    @Column
    private String name;

    @Column
    private Integer productQuantity;

    @Column
    private Integer productAgeDiscountThreshold;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "product-quotation")
    private List<Quotation> quotationsList;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "product-discount")
    private List<Discount> discountList;

    public Product() {
    }


    public Product(Long id, ProductType productType, Integer productPrice, String name, Integer productQuantity, Integer productAgeDiscountThreshold, List<Quotation> quotationsList, List<Discount> discountList) {
        this.id = id;
        this.productType = productType;
        this.productPrice = productPrice;
        this.name = name;
        this.productQuantity = productQuantity;
        this.productAgeDiscountThreshold = productAgeDiscountThreshold;
        this.quotationsList = quotationsList;
        this.discountList = discountList;
    }

    public Long getId() {
        return id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getProductAgeDiscountThreshold() {
        return productAgeDiscountThreshold;
    }

    public void setProductAgeDiscountThreshold(Integer productAgeDiscountThreshold) {
        this.productAgeDiscountThreshold = productAgeDiscountThreshold;
    }

    public List<Quotation> getQuotationsList() {
        if (this.quotationsList == null) {
            quotationsList = new ArrayList<>();
        }
        return quotationsList;
    }

    public void setQuotationsList(List<Quotation> quotationsList) {
        this.quotationsList = quotationsList;
    }

    public List<Discount> getDiscountList() {
        if (this.discountList == null) {
            discountList = new ArrayList<>();
        }
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }
}
