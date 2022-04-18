package org.example.controller;

import org.example.dto.CustomerDTO;
import org.example.service.CustomerRegisterationEnum;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerRegisterationEnum> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerRegisterationEnum customerRegistrationStatus = customerService.registerCustomer(customerDTO);
        return new ResponseEntity<>(customerRegistrationStatus, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Integer id){
        if (customerService.deleteCustomerById(id)){
            return new ResponseEntity<>("Account Successfully Deleted",HttpStatus.OK);
        } else return new ResponseEntity<>("Error While Deleting Customer Account", HttpStatus.NOT_FOUND);
    }
}
