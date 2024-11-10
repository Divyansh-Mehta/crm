package com.assignment.crm.service;

import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.model.Sales;
import com.assignment.crm.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportServiceTest {
    @Mock
    private InteractionLogService interactionLogService;

    @Mock
    private SalesService salesService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ReportServiceImpl reportService;

    private List<InteractionLog> interactionLogs;
    private List<Sales> salesList;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        // Set up logs
        interactionLogs = new ArrayList<>();
        InteractionLog log1 = new InteractionLog();
        log1.setType("phone call");
        InteractionLog log2 = new InteractionLog();
        log2.setType("email");
        InteractionLog log3 = new InteractionLog();
        log3.setType("demo session");
        interactionLogs.add(log1);
        interactionLogs.add(log2);
        interactionLogs.add(log3);

        // Set up sales
        salesList = new ArrayList<>();
        Sales sale1 = new Sales();
        sale1.setDealSize(1000);
        sale1.setCreatedAt(LocalDateTime.now().minusDays(5));
        sale1.setClosingDate(LocalDateTime.now().minusDays(1));
        salesList.add(sale1);

        // Set up customers
        customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setSales(salesList);
        customers.add(customer1);
        Customer customer2 = new Customer();
        customer2.setSales(new ArrayList<>());
        customers.add(customer2);
    }

    @Test
    void testGenerateCustomerInteractionReport() {
        Long customerId = 1L;
        when(interactionLogService.getAllInteractionLogsOfACustomer(customerId)).thenReturn(interactionLogs);

        CustomerInteractionReportDto result = reportService.generateCustomerInteractionReport(customerId);

        assertNotNull(result);
        assertEquals(3, result.getInteractionCount());
        assertEquals(1, result.getPhoneCallCount());
        assertEquals(1, result.getEmailCount());
        assertEquals(1, result.getDemoSessionCount());
        assertEquals(0, result.getInPersonCount());
        assertEquals(0, result.getMiscellaneousCount());
    }

    @Test
    void testGenerateSalesPerformanceReport() {
        Long customerId = 1L;
        when(salesService.getAllSales(customerId)).thenReturn(salesList);

        SalesPerformanceReportDto result = reportService.generateSalesPerformanceReport(customerId);

        assertNotNull(result);
        assertEquals(1, result.getTotalDeals());
        assertEquals(1000, result.getTotalDealValue());
        assertEquals(1, result.getTotalClosedDeals());
        assertTrue(result.getAverageTimeToClose() > 0);
    }

    @Test
    void testGenerateCustomerGrowthAndRetentionReport() {
        when(customerService.getAllCustomers()).thenReturn(customers);

        CustomerGrowthAndRetentionReportDto result = reportService.generateCustomerGrowthAndRetentionReport();

        assertNotNull(result);
        assertEquals(1, result.getTotalCustomer());
        assertEquals(0, result.getRetainedCustomer());
        assertEquals(1, result.getNonRetainedCustomer());
    }
}
