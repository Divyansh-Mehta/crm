package com.assignment.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stage;
    private double dealSize;
    private double probabilityOfClosing;
    private LocalDateTime createdAt;
    private LocalDateTime closingDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "sales", orphanRemoval = true)
    private List<InteractionLog> interactionLogs;
}
