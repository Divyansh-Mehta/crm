package com.assignment.crm.controller;

import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import com.assignment.crm.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportControllerTest {
    @Mock
    private ReportServiceImpl reportService;

    @InjectMocks
    private ReportController reportController;

    private CustomerInteractionReportDto customerInteractionReportDto;
    private SalesPerformanceReportDto salesPerformanceReportDto;
    private CustomerGrowthAndRetentionReportDto customerGrowthAndRetentionReportDto;

    @BeforeEach
    void setUp() {
        // Initialize DTOs
        customerInteractionReportDto = new CustomerInteractionReportDto();
        salesPerformanceReportDto = new SalesPerformanceReportDto();
        customerGrowthAndRetentionReportDto = new CustomerGrowthAndRetentionReportDto();
    }

    @Test
    void testGetCustomerInteractionReport() {
        when(reportService.generateCustomerInteractionReport(1L)).thenReturn(customerInteractionReportDto);

        ResponseEntity<CustomerInteractionReportDto> response;
        response = reportController.getCustomerInteractionReport(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerInteractionReportDto, response.getBody());
    }

    @Test
    void testGetSalesPerformanceReport() {
        when(reportService.generateSalesPerformanceReport(1L)).thenReturn(salesPerformanceReportDto);

        ResponseEntity<SalesPerformanceReportDto> response = reportController.getSalesPerformanceReport(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salesPerformanceReportDto, response.getBody());
    }

    @Test
    void testGetCustomerGrowthAndRetentionReport() {
        when(reportService.generateCustomerGrowthAndRetentionReport()).thenReturn(customerGrowthAndRetentionReportDto);

        ResponseEntity<CustomerGrowthAndRetentionReportDto> response = reportController.getCustomerGrowthAndRetentionReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerGrowthAndRetentionReportDto, response.getBody());
    }
}
