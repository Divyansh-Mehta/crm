package com.assignment.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInteractionReportDto {
    private long interactionCount;
    private long phoneCallCount;
    private long emailCount;
    private long inPersonCount;
    private long demoSessionCount;
    private long miscellaneousCount;
}
