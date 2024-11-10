package com.assignment.crm.controller;

import com.assignment.crm.dto.CustomerDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.service.impl.CustomerServiceImpl;
import com.assignment.crm.utils.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        // Initialize Customer
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Divyansh Mehta");
        customer.setEmail("divyanshmehta@gmail.com");
        customer.setPhone("9182736405");

        // Initialize CustomerDto
        customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(customerDto.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetCustomerByIdSuccess() throws CustomException {
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDto.getId(), response.getBody().getId());
    }

    @Test
    void testGetCustomerByIdFailure() {
        when(customerService.getCustomerById(1L)).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> {
            customerController.getCustomerById(1L);
        });

        assertEquals("Cannot find customer with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testCreateCustomer() {
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        ResponseEntity<CustomerDto> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDto.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateCustomerSuccess() throws CustomException {
        when(customerService.updateCustomer(customer)).thenReturn(true);

        ResponseEntity<String> response = customerController.updateCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer updated successfully", response.getBody());
    }

    @Test
    void testUpdateCustomerFailure() {
        when(customerService.updateCustomer(customer)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            customerController.updateCustomer(customer);
        });

        assertEquals("Cannot find customer with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testDeleteCustomerSuccess() throws CustomException {
        when(customerService.deleteCustomer(1L)).thenReturn (true);

        ResponseEntity<String> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully", response.getBody());
    }

    @Test
    void testDeleteCustomerFailure() {
        when(customerService.deleteCustomer(2L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            customerController.deleteCustomer(2L);
        });

        assertEquals("Cannot find customer with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}