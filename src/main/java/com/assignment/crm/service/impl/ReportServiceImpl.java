package com.assignment.crm.service.impl;

import com.assignment.crm.dto.CustomerGrowthAndRetentionReportDto;
import com.assignment.crm.dto.CustomerInteractionReportDto;
import com.assignment.crm.dto.SalesPerformanceReportDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.model.Sales;
import com.assignment.crm.service.CustomerService;
import com.assignment.crm.service.InteractionLogService;
import com.assignment.crm.service.ReportService;
import com.assignment.crm.service.SalesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final InteractionLogService interactionLogService;
    private final SalesService salesService;
    private final CustomerService customerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    public ReportServiceImpl(InteractionLogService interactionLogService, SalesService salesService, CustomerService customerService) {
        this.interactionLogService = interactionLogService;
        this.salesService = salesService;
        this.customerService = customerService;
    }

    @Override
    public CustomerInteractionReportDto generateCustomerInteractionReport(Long customerId) {
        List<InteractionLog> interactionLogs = interactionLogService.getAllInteractionLogsOfACustomer(customerId);
        long interactionCount = 0L;
        long phoneCallCount = 0L;
        long emailCount = 0L;
        long inPersonCount = 0L;
        long demoSessionCount = 0L;
        long miscellaneousCount = 0L;

        for (InteractionLog log: interactionLogs){
            interactionCount++;
            String type = log.getType().toLowerCase();
            if (type.equals("phone call")){
                phoneCallCount++;
            }
            else if (type.equals("email")){
                emailCount++;
            }
            else if (type.equals("in person")){
                inPersonCount++;
            }
            else if (type.equals("demo session")){
                demoSessionCount++;
            }
            else {
                miscellaneousCount++;
            }
        }

        CustomerInteractionReportDto report = new CustomerInteractionReportDto();
        report.setInteractionCount(interactionCount);
        report.setPhoneCallCount(phoneCallCount);
        report.setEmailCount(emailCount);
        report.setInPersonCount(inPersonCount);
        report.setDemoSessionCount(demoSessionCount);
        report.setMiscellaneousCount(miscellaneousCount);

        LOGGER.info("Customer Interaction report generated for customer with id {}", customerId);
        return report;
    }

    @Override
    public SalesPerformanceReportDto generateSalesPerformanceReport(Long customerId) {
        List<Sales> salesList = salesService.getAllSales(customerId);
        long totalDeals = 0L;
        double totalDealValue = 0;
        long totalClosedDeals = 0;
        double totalDurationInSeconds = 0;
        double averageTimeToClose;

        for (Sales sales: salesList){
            totalDeals++;
            if (sales.getClosingDate() != null){
                totalDealValue += sales.getDealSize();
                totalClosedDeals++;
                Duration duration = Duration.between(sales.getCreatedAt(), sales.getClosingDate());
                totalDurationInSeconds += duration.getSeconds();
            }
        }
        averageTimeToClose = (totalDurationInSeconds / totalClosedDeals) / 3600;

        SalesPerformanceReportDto report = new SalesPerformanceReportDto();

        report.setTotalDeals(totalDeals);
        report.setTotalDealValue(totalDealValue);
        report.setTotalClosedDeals(totalClosedDeals);
        report.setAverageTimeToClose(averageTimeToClose);

        LOGGER.info("Sales performance report generated for customer with id {}", customerId);
        return report;
    }

    @Override
    public CustomerGrowthAndRetentionReportDto generateCustomerGrowthAndRetentionReport() {
        long totalCustomer = 0L;
        long retainedCustomer = 0L;
        long nonRetainedCustomer = 0L;

        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer: customers){
            if (!customer.getSales().isEmpty()){
                totalCustomer++;
            }
            if (customer.getSales().size() > 1){
                retainedCustomer++;
            }
            else if (customer.getSales().size() == 1){
                nonRetainedCustomer++;
            }
        }

        CustomerGrowthAndRetentionReportDto report = new CustomerGrowthAndRetentionReportDto();

        report.setTotalCustomer(totalCustomer);
        report.setRetainedCustomer(retainedCustomer);
        report.setNonRetainedCustomer(nonRetainedCustomer);

        LOGGER.info("Customer Growth and Retention report generated");
        return report;
    }
}
