package com.assignment.crm.integration;

import com.assignment.crm.controller.ReportController;
import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import com.assignment.crm.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ReportApisTest {
    @Autowired
    private MockMvc mockMvc;

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
    void testGetCustomerInteractionReport() throws Exception {
        when(reportService.generateCustomerInteractionReport(1L)).thenReturn(customerInteractionReportDto);

        mockMvc.perform(get("/api/reports/customer-interaction-report/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetSalesPerformanceReport() throws Exception {
        when(reportService.generateSalesPerformanceReport(1L)).thenReturn(salesPerformanceReportDto);

        mockMvc.perform(get("/api/reports/sales-performance-report/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetCustomerGrowthAndRetentionReport() throws Exception {
        when(reportService.generateCustomerGrowthAndRetentionReport()).thenReturn(customerGrowthAndRetentionReportDto);

        mockMvc.perform(get("/api/reports/customer-growth-and-retention-report")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
