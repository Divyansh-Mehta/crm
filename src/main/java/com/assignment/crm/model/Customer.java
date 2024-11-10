package com.assignment.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<Sales> sales;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InteractionLog> interactionLogs;
}
