package com.assignment.crm.service;

import com.assignment.crm.model.Customer;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.SalesRepository;
import com.assignment.crm.service.impl.SalesServiceImpl;
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
public class SalesServiceTest {
    @Mock
    private SalesRepository salesRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private SalesServiceImpl salesService;

    private Sales sales;
    private Customer customer;

    @BeforeEach
    void setUp() {
        // Initialize Customer
        customer = new Customer();
        customer.setId(1L);

        // Initialize Sales
        sales = new Sales();
        sales.setId(1L);
        sales.setDealSize(1000.0);
        sales.setProbabilityOfClosing(0.75);
        sales.setCustomer(customer);
    }

    @Test
    void testGetAllSales() {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(sales);

        when(salesRepository.findAllByCustomerId(1L)).thenReturn(salesList);

        List<Sales> result = salesService.getAllSales(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetSalesById() {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        Sales result = salesService.getSalesById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testCreateSales() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(salesRepository.save(sales)).thenReturn(sales);

        Sales result = salesService.createSales(sales, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateSalesSuccess() {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        Sales updatedSales = new Sales();
        updatedSales.setId(1L);
        updatedSales.setDealSize(1500.0);
        updatedSales.setProbabilityOfClosing(0.80);

        boolean result = salesService.updateSales(updatedSales);

        assertTrue(result);
    }

    @Test
    void testUpdateSalesFailure() {
        when(salesRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = salesService.updateSales(sales);

        assertFalse(result);
    }

    @Test
    void testDeleteSalesSuccess() {
        when(salesRepository.existsById(1L)).thenReturn(true);

        boolean result = salesService.deleteSales(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteSalesFailure() {
        when(salesRepository.existsById(2L)).thenReturn(false);

        boolean result = salesService.deleteSales(2L);

        assertFalse(result);
    }

    @Test
    void testUpdateStageSuccess() {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        boolean result = salesService.updateStage(1L, "Closed");

        assertTrue(result);
        assertEquals("Closed", sales.getStage());
    }

    @Test
    void testUpdateStageFailure() {
        when(salesRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = salesService.updateStage(1L, "Closed");

        assertFalse(result);
    }

    @Test
    void testCloseDealSuccess() {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        boolean result = salesService.closeDeal(1L);

        assertTrue(result);
    }

    @Test
    void testCloseDealFailure() {
        when(salesRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = salesService.closeDeal(2L);

        assertFalse(result);
    }
}