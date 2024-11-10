package com.assignment.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesPerformanceReportDto {
    private long totalDeals;
    private double totalDealValue;
    private long totalClosedDeals;
    private double averageTimeToClose;
}
