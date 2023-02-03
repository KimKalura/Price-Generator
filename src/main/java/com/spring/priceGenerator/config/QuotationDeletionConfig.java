package com.spring.priceGenerator.config;

import com.spring.priceGenerator.model.Quotation;
import com.spring.priceGenerator.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuotationDeletionConfig {
    private QuotationRepository quotationRepository;


    @Autowired
    public QuotationDeletionConfig(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);

        List<Quotation> allQuotations = quotationRepository.findAll();
        List<Quotation> expiredQuotations = allQuotations.stream()
                .filter(quotation -> quotation.getExpireDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        quotationRepository.deleteAll(expiredQuotations);

    }
}
