package com.assignment.crm.service;

import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.repository.CustomerRepository;
import com.assignment.crm.service.impl.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Divyansh Mehta");
        customer.setEmail("divyanshmehta@gmail.com");
        customer.setPhone("9182736405");
        customer.setInteractionLogs(new ArrayList<>());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals(1L, result.getId());
        assertNotNull(result);
    }

    @Test
    void testSaveCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.saveCustomer(customer);

        assertEquals(1L, result.getId());
        assertNotNull(result);
    }

    @Test
    void testUpdateCustomerSuccess() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Divyansh");
        updatedCustomer.setEmail("divyanshmehta01@gmail.com");
        updatedCustomer.setPhone("9876543201");

        boolean result = customerService.updateCustomer(updatedCustomer);

        assertTrue(result);
    }

    @Test
    void testUpdateCustomerFailure() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(2L); // Non-existing customer ID

        boolean result = customerService.updateCustomer(updatedCustomer);

        assertFalse(result);
    }

    @Test
    void testDeleteCustomerSuccess() {
        when(customerRepository.existsById(1L)).thenReturn(true);

        boolean result = customerService.deleteCustomer(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteCustomerFailure() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        boolean result = customerService.deleteCustomer(1L);

        assertFalse(result);
    }

    @Test
    void testAddInteractionLogSuccess() {
        InteractionLog interactionLog = new InteractionLog();
        interactionLog.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        boolean result = customerService.addInteractionLog(1L, interactionLog);
        assertTrue(result);
        assertTrue(customer.getInteractionLogs().contains(interactionLog));
    }

    @Test
    void testAddInteractionLogFailure() {
        InteractionLog interactionLog = new InteractionLog();
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = customerService.addInteractionLog(2L, interactionLog);
        assertFalse(result);
    }

    @Test
    void testRemoveInteractionLogSuccess() {
        InteractionLog interactionLog = new InteractionLog();
        interactionLog.setId(1L);
        customer.getInteractionLogs().add(interactionLog);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        boolean result = customerService.removeInteractionLog(1L, interactionLog);

        assertTrue(result);
        assertFalse(customer.getInteractionLogs().contains(interactionLog));
    }

    @Test
    void testRemoveInteractionLogFailure() {
        InteractionLog interactionLog = new InteractionLog();
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = customerService.removeInteractionLog(2L, interactionLog);

        assertFalse(result);
    }
}
