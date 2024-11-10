package com.assignment.crm.integration;

import com.assignment.crm.controller.CustomerController;
import com.assignment.crm.dto.CustomerDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.repository.CustomerRepository;
import com.assignment.crm.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerApisTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        // Initialize Customer
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Divyansh Mehta");
        customer.setEmail("divyanshmehta@gamil.com");
        customer.setPhone("9182736405");

        // Initialize CustomerDto
        customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(customerDto.getId()))
                .andExpect(jsonPath("$[0].name").value(customerDto.getName()))
                .andExpect(jsonPath("$[0].email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customerDto.getPhone()));
    }

    @Test
    void testGetCustomerByIdSuccess() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.name").value(customerDto.getName()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$.phone").value(customerDto.getPhone()));

    }

    @Test
    void testGetCustomerByIdFailure() throws Exception {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/customers/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCustomer() throws Exception {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Divyansh Mehta\", \"email\": \"divyanshmehta@gmail.com\", \"phone\": \"9182736405\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(customerDto.getName()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$.phone").value(customerDto.getPhone()));
    }

    @Test
    void testUpdateCustomerSuccess() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Divyansh Mehta\", \"email\": \"divyanshmehta@gmail.com\", \"phone\": \"9182736405\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer updated successfully"));
    }

    @Test
    void testUpdateCustomerFailure() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
//        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Divyansh Mehta\", \"email\": \"divyanshmehta@gmail.com\", \"phone\": \"9182736405\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCustomerSuccess() throws Exception {
        when(customerRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully"));

    }

    @Test
    void testDeleteCustomerFailure() throws Exception {
        when(customerRepository.existsById(2L)).thenReturn(false);

        mockMvc.perform(delete("/api/customers/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
