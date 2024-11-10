package com.assignment.crm.controller;

import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import com.assignment.crm.service.ReportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/customer-interaction-report/{id}")
    public ResponseEntity<CustomerInteractionReportDto> getCustomerInteractionReport(@PathVariable("id") Long customerId){
        return new ResponseEntity<>(reportService.generateCustomerInteractionReport(customerId), HttpStatus.OK);
    }

    @GetMapping("/sales-performance-report/{id}")
    public ResponseEntity<SalesPerformanceReportDto> getSalesPerformanceReport(@PathVariable("id") Long customerId){
        return new ResponseEntity<>(reportService.generateSalesPerformanceReport(customerId), HttpStatus.OK);
    }

    @GetMapping("/customer-growth-and-retention-report")
    public ResponseEntity<CustomerGrowthAndRetentionReportDto> getCustomerGrowthAndRetentionReport(){
        return new ResponseEntity<>(reportService.generateCustomerGrowthAndRetentionReport(), HttpStatus.OK);
    }
}
