package com.spring.priceGenerator.service;

import com.spring.priceGenerator.dto.OrderDTO;
import com.spring.priceGenerator.dto.OrderItemDTO;
import com.spring.priceGenerator.model.Order;
import com.spring.priceGenerator.model.Product;
import com.spring.priceGenerator.model.Quotation;
import com.spring.priceGenerator.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private QuotationService quotationService;

    private UserService userService;

    @Autowired
    public OrderService(UserService userService, OrderRepository orderRepository, QuotationService quotationService) {
        this.orderRepository = orderRepository;
        this.quotationService = quotationService;
        this.userService = userService;
    }

    //Faci o comanda pentru una, doua… sau toate cotatiile (ofertele de pret) active
    //*Aici se va aplica, dupa caz, si reducerea pentru numarul de cotatii din comanda, care practic este egal cu numarul de produse (pentru ca o cotatie se genereaza pentru un produs)
    public Order addOrder(OrderDTO orderDTO) {
        //pentru a verifica daca sunt cotatiile active ne folosim de met getActiveQuotation() care returneaza o lista

        List<Quotation> availableQuotationList = quotationService.getActiveQuotation();
        List<Integer> noOfProducts = new ArrayList<>();

        Order newOrder = new Order();
        newOrder.setCreatedDate(LocalDateTime.now());
        newOrder.setUser(userService.findLoggedInUser());

        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOList()) {
            int positionOfQuotationInActiveQuotationList = isAvailableQuotation(orderItemDTO, availableQuotationList);
            if (positionOfQuotationInActiveQuotationList == -1) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the quotation is not more available");
            } else {
                newOrder.getQuotationList().add(availableQuotationList.get(positionOfQuotationInActiveQuotationList));
                noOfProducts.add(orderItemDTO.getQuantity());
                Product currentProduct = availableQuotationList.get(positionOfQuotationInActiveQuotationList).getProduct();
                if (currentProduct.getProductQuantity() - orderItemDTO.getQuantity() <= 0) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there are not enough products in stock");
                }
                currentProduct.setProductQuantity(currentProduct.getProductQuantity() - orderItemDTO.getQuantity());
            }
        }
        Double discountByNumberOfProducts = getDiscountByNoOfProducts(noOfProducts);
        Double totalDiscount = getTotalDiscount(newOrder.getQuotationList(), noOfProducts, discountByNumberOfProducts);
        newOrder.setTotalPrice(computeTotalPrice(newOrder.getQuotationList(), noOfProducts, totalDiscount));
        return orderRepository.save(newOrder);
    }


    public int isAvailableQuotation(OrderItemDTO orderItemDTO, List<Quotation> availableQuotationList) {
        for (int i = 0; i < availableQuotationList.size(); i++) {
            if (availableQuotationList.get(i).getId() == orderItemDTO.getQuotationId()) {
                return i;
            }
        }
        return -1;
    }

    //clientul primeste o reducere de 0.5% per produs, daca cumpara minim 3 produse.
    //Reducerea se aplica pentru maxim 10 produse cumparate (De exemplu, reducerea totala va fi de 4,5% pentru produsele 2,3,4,5,6,7,8,9,10, daca clientul cumpara 15 produse)
    public Double getDiscountByNoOfProducts(List<Integer> noOfProducts) {
        if ((noOfProducts.size() >= 3) && (noOfProducts.size() <= 10)) {
            return noOfProducts.size() * 0.5;
        }
        return 0.0;
    }

    public Double getTotalDiscount(List<Quotation> quotationList, List<Integer> numberOfProducts, Double discountByNumberOfProducts) {
        Double totalDiscount = 0.0;
        for (int i = 0; i < quotationList.size(); i++) {
            if (i < 10) {
                totalDiscount += numberOfProducts.get(i) * (quotationList.get(i).getAgeDiscount() + quotationList.get(i).getCountryDiscount() + quotationList.get(i).getProduct().getProductPrice() * discountByNumberOfProducts);
            } else {
                totalDiscount += numberOfProducts.get(i) * (quotationList.get(i).getAgeDiscount() + quotationList.get(i).getCountryDiscount());
            }
        }
        return totalDiscount;
    }

    public Double computeTotalPrice(List<Quotation> quotationList, List<Integer> noOfProducts, Double totalDiscount) {
//        Optional<Double> totalPrice = quotationList.stream()
//                .map(quotation -> quotation.getProduct().getProductPrice() * noOfProducts)

        Double totalPrice = 0.0;
        for (int i = 0; i < quotationList.size(); i++) {
            totalPrice += quotationList.get(i).getProduct().getProductPrice() * noOfProducts.get(i);
        }
        return totalPrice - totalDiscount;
    }

}

