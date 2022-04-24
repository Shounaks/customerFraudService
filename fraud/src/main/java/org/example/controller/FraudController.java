package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.FraudCheckResponse;
import org.example.service.FraudCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/fraud-check")
public class FraudController {


    @Autowired
    private FraudCheckService fraudCheckService;

    @Autowired
    public FraudController(FraudCheckService fraudCheckService) {
        this.fraudCheckService = fraudCheckService;
    }

    @GetMapping("/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable Integer customerId){
        boolean isFraudlentCustomer = fraudCheckService.isFraudlentCustomer(customerId);
        log.info("Customer id: "+ customerId + " is " + ((isFraudlentCustomer)?"fraud":"not a fraud"));
        return new FraudCheckResponse(isFraudlentCustomer);
    }
}
