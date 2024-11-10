package com.assignment.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InteractionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private LocalDate interactionDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;
}
