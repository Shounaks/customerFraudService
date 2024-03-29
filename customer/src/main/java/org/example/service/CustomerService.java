package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.CustomerDTO;
import org.example.dto.FraudCheckResponse;
import org.example.entity.Customer;
import org.example.exception.CustomerNotFoundException;
import org.example.mapping.CustomerMapper;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;

    public CustomerDTO getCustomerById(Integer id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Given CustomerID does not Exist"));
        return  CustomerMapper.toDTO(customer);
    }

    public CustomerRegisterationEnum registerCustomer(CustomerDTO customerDTO){
        log.info("New Customer Registeration: {}",customerDTO);
        if (customerRepository.findByEmailEquals(customerDTO.email()).isPresent()){
            log.info("ERROR: Email Already Registered: {}", customerDTO.email());
            return CustomerRegisterationEnum.EMAIL_ALREADY_REGISTERED;
        }
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        log.info("Saved Customer using the following ID: {}", savedCustomer.getId());
        
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                "http://FRAUDMS/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                savedCustomer.getId()
        );
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("User is a Known Fraud");
        }
        return CustomerRegisterationEnum.SUCCESS;
    }

    public boolean deleteCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customerRepository.delete(customer);
            return true;
        }else return false;
    }
}
