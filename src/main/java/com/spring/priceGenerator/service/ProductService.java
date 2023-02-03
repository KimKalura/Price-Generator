package com.spring.priceGenerator.service;

import com.spring.priceGenerator.dto.AddProductDTO;
import com.spring.priceGenerator.dto.DiscountDTO;
import com.spring.priceGenerator.dto.QuotationDTO;
import com.spring.priceGenerator.model.*;
import com.spring.priceGenerator.repository.CountryRepository;
import com.spring.priceGenerator.repository.ProductRepository;
import com.spring.priceGenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public Product addProduct(AddProductDTO addProductDTO) {
        Optional<Product> foundProduct = productRepository.findProductByName(addProductDTO.getProductName());
        if (foundProduct.isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "This product name already exist");
        }
        Product productToBeAdd = new Product();
        productToBeAdd.setName(addProductDTO.getProductName());
        productToBeAdd.setProductType(addProductDTO.getProductType());
        productToBeAdd.setProductPrice(addProductDTO.getProductPrice());
        productToBeAdd.setProductQuantity(addProductDTO.getProductQuantity());
        productToBeAdd.setProductAgeDiscountThreshold(addProductDTO.getProductAgeDiscountThreshold());
        generateDiscountsToProduct(addProductDTO, productToBeAdd);
        //generateQuotationToProduct(addProductDTO, productToBeAdd);
        return productRepository.save(productToBeAdd);
    }

    private void generateDiscountsToProduct(AddProductDTO addProductDTO, Product productToBeAdd) {
        for (DiscountDTO discountDTO : addProductDTO.getDiscounts()) {
            Country foundCountry = countryRepository.findCountryByCountryName(discountDTO.getCountry());
            if (foundCountry == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, " country not found");
            }
            Discount discount = new Discount();
            discount.setDiscount(discountDTO.getDiscount());
            discount.setProduct(productToBeAdd);

            productToBeAdd.getDiscountList().add(discount);
            discount.setCountry(foundCountry);
        }
    }
//    private void generateQuotationToProduct(AddProductDTO addProductDTO, Product productToBeAdd) {
//        for (QuotationDTO quotationDTO : addProductDTO.getQuotationList()) {
//            Optional<User> foundUser = userRepository.findUserByUsername(quotationDTO.getUser());
//            if (foundUser == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
//            }
//            Quotation quotation = new Quotation();
//            quotation.setUser(quotation.getUser());
//            quotation.setProduct(productToBeAdd);
//            productToBeAdd.getQuotationsList().add(quotation);
//            quotation.setExpireDate(quotation.getExpireDate());
//        }
//    }

    public void deleteProduct(Long productId) {
        Product foundProduct = productRepository.findProductById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        productRepository.delete(foundProduct);
    }
}
