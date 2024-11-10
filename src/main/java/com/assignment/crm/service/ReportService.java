package com.assignment.crm.service;

import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    CustomerInteractionReportDto generateCustomerInteractionReport(Long customerId);
    SalesPerformanceReportDto generateSalesPerformanceReport(Long customerId);
    CustomerGrowthAndRetentionReportDto generateCustomerGrowthAndRetentionReport();
}
