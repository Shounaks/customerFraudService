package org.example.mapping;


import org.example.dto.CustomerDTO;
import org.example.entity.Customer;


public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        return Customer.builder()
                .firstName(customerDTO.firstName())
                .lastName(customerDTO.lastName())
                .email(customerDTO.email())
                .build();
    }
}
