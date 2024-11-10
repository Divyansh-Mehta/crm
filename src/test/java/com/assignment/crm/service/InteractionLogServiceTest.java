package com.assignment.crm.service;

import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.InteractionLogRepository;
import com.assignment.crm.service.impl.InteractionLogServiceImpl;
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
public class InteractionLogServiceTest {
    @Mock
    private InteractionLogRepository interactionLogRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private SalesService salesService;

    @InjectMocks
    private InteractionLogServiceImpl interactionLogService;

    private InteractionLog interactionLog;
    private Sales sales;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Divyansh Mehta");

        sales = new Sales();
        sales.setId(1L);
        sales.setCustomer(customer);

        interactionLog = new InteractionLog();
        interactionLog.setId(1L);
        interactionLog.setSales(sales);
        interactionLog.setNotes("Positive Response");
    }

    @Test
    void testGetAllInteractionLogsOfASales() {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);
        when(interactionLogRepository.findAllBySalesId(1L)).thenReturn(logs);

        List<InteractionLog> result = interactionLogService.getAllInteractionLogsOfASales(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetAllInteractionLogsOfACustomer() {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);
        when(interactionLogRepository.findAllByCustomerId(1L)).thenReturn(logs);

        List<InteractionLog> result = interactionLogService.getAllInteractionLogsOfACustomer(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetInteractionLogById() {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        InteractionLog result = interactionLogService.getInteractionLogById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveInteractionLog() {
        when(salesService.getSalesById(1L)).thenReturn(sales);
        when(interactionLogRepository.save(interactionLog)).thenReturn(interactionLog);
        when(customerService.addInteractionLog(1L, interactionLog)).thenReturn(true);

        InteractionLog result = interactionLogService.saveInteractionLog(interactionLog, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateInteractionLogSuccess() {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));
        interactionLog.setNotes("Updated log");

        boolean result = interactionLogService.updateInteractionLog(interactionLog);

        assertTrue(result);
    }

    @Test
    void testUpdateInteractionLog_Failure() {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = interactionLogService.updateInteractionLog(interactionLog);

        assertFalse(result);
    }

    @Test
    void testDeleteInteractionLogSuccess() {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));
        when(customerService.removeInteractionLog(1L, interactionLog)).thenReturn(true);

        boolean result = interactionLogService.deleteInteractionLog(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteInteractionLogFailure() {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = interactionLogService.deleteInteractionLog(2L);

        assertFalse(result);
    }

    @Test
    void testAddNotesSuccess() {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        boolean result = interactionLogService.addNotes("New notes", 1L);

        assertTrue(result);
        assertEquals("New notes", interactionLog.getNotes());
    }

    @Test
    void testAddNotesFailure() {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = interactionLogService.addNotes("New notes", 2L);

        assertFalse(result);
    }
}
