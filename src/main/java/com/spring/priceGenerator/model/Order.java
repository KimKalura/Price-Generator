package com.spring.priceGenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime createdDate;

    @ManyToOne
    @JsonBackReference(value = "user-order")
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "order-quotation")
    private List<Quotation> quotationList;

    @Column
    Double totalPrice;

    public Order(){}

    public Order(Long id, LocalDateTime createdDate, User user, List<Quotation> quotationList) {
        this.id = id;
        this.createdDate = createdDate;
        this.user = user;
        this.quotationList = quotationList;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Quotation> getQuotationList() {
        if (this.quotationList == null) {
            quotationList = new ArrayList<>();
        }
        return quotationList;
    }

    public void setQuotationList(List<Quotation> quotationList) {
        this.quotationList = quotationList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
