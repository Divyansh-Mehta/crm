package com.assignment.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionLogDto {
    private Long id;
    private String type;
    private LocalDate interactionDate;
    private String notes;
}
