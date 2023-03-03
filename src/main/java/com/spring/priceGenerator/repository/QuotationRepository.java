package com.spring.priceGenerator.repository;

import com.spring.priceGenerator.model.Quotation;
import com.spring.priceGenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
   // List<Quotation> findAllByUser(User user);

}
