package com.assignment.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGrowthAndRetentionReportDto {
    private long totalCustomer;
    private long retainedCustomer;
    private long nonRetainedCustomer;
}
