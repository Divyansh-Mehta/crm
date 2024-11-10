package com.assignment.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Long id;
    private String stage;
    private double dealSize;
    private double probabilityOfClosing;
    private LocalDateTime createdAt;
    private LocalDateTime closingDate;
}
