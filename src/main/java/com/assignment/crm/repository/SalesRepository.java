package com.assignment.crm.repository;

import com.assignment.crm.model.Sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query("SELECT s FROM Sales s WHERE s.customer.id = :customerId")
    List<Sales> findAllByCustomerId(Long customerId);
}
