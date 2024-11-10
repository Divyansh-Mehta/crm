package com.assignment.crm.controller;

import com.assignment.crm.dto.CustomerDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.service.CustomerService;
import com.assignment.crm.utils.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
       Get all customers
    1. Use customer service to get list of all customers
    2. Convert list of customers to list of customerDtos
    3. Return a response entity
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer: customers){
            CustomerDto customerDto = createCustomerDtoFromCustomer(customer);
            customerDtos.add(customerDto);
        }
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    /*
       Get customer by id
    1. Use customer service to get required customer
    2. If customer is null throw CustomException
    3. If customer is not null convert it to customerDto
    4. Return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) throws CustomException{
        Customer customer = customerService.getCustomerById(id);
        if (customer == null){
            throw new CustomException("Cannot find customer with given id", HttpStatus.NOT_FOUND);
        }
        CustomerDto customerDto = createCustomerDtoFromCustomer(customer);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /*
       Save a customer
    1. Use customer service to save the customer
    2. Convert the saved customer to customerDto
    3. Return response entity
     */
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer){
        Customer SavedCustomer = customerService.saveCustomer(customer);
        CustomerDto customerDto = createCustomerDtoFromCustomer(SavedCustomer);
        return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
    }

    /*
       Update customer
    1. Use customer service to update customer
    2. If customer is not updated throw customException
    3. If customer is updated return a response Entity
     */
    @PutMapping
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws CustomException{
        System.out.print(customer.getId());
        boolean isUpdated = customerService.updateCustomer(customer);
        if (!isUpdated){
            throw new CustomException("Cannot find customer with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    /*
       Delete customer
    1. Use customer service to delete a customer
    2. If customer is not deleted throw customException
    3. If customer is deleted return response Entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) throws CustomException {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (!isDeleted) {
            throw new CustomException("Cannot find customer with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    /*
       Helper function to convert customer to customerDto
    1. Create a customerDto object
    2. Set its fields from customer object
    3. Return the customerDto object
     */
    private CustomerDto createCustomerDtoFromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }
}
