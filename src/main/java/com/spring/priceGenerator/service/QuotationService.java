package com.spring.priceGenerator.service;

import com.spring.priceGenerator.model.Discount;
import com.spring.priceGenerator.model.Product;
import com.spring.priceGenerator.model.Quotation;
import com.spring.priceGenerator.model.User;
import com.spring.priceGenerator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationService {
    private QuotationRepository quotationRepository;

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CountryRepository countryRepository;
    private DiscountRepository discountRepository;
    private UserService userService;



    @Autowired
    public QuotationService(QuotationRepository quotationRepository, ProductRepository productRepository, UserRepository userRepository, DiscountRepository discountRepository, CountryRepository countryRepository, UserService userService) {
        this.quotationRepository = quotationRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
        this.countryRepository = countryRepository;
        this.userService = userService;
    }


    public Quotation generateQuotation(Long productId) {
        Product foundProduct = productRepository.findProductById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        User foundUser = userService.findLoggedInUser();
        Integer foundUserAge = Period.between(foundUser.getDateOfBirth(), LocalDate.now()).getYears();
        Quotation quotation = new Quotation();
        if (foundUserAge >= foundProduct.getProductAgeDiscountThreshold()) {
            quotation.setAgeDiscount(foundProduct.getProductPrice() * 0.2);
        }

        String foundUserCountry = foundUser.getCountry();
        Discount countryDiscount = discountRepository.findDiscountByCountry_CountryNameAndProduct(foundUserCountry, foundProduct);
        quotation.setCountryDiscount(foundProduct.getProductPrice() * countryDiscount.getDiscount() / 100);

        quotation.setProduct(foundProduct);
        quotation.setUser(foundUser);
        quotation.setExpireDate(LocalDateTime.now().plusMinutes(5));
        return quotationRepository.save(quotation);
    }


    public List<Quotation> getActiveQuotation() {
//        List<Quotation> availableQuotations = new ArrayList<>();
//        for (Quotation quotation : foundUser.getQuotationList()) {
//            if (quotation.getExpireDate().isAfter(LocalDateTime.now())) {
//                availableQuotations.add(quotation);
//            }
//        }
//        return availableQuotations;
        return userService.findLoggedInUser().getQuotationList().stream()
                .filter(quotation -> quotation.getExpireDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
