package com.assignment.crm.repository;

import com.assignment.crm.model.InteractionLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionLogRepository extends JpaRepository<InteractionLog, Long> {
    @Query("SELECT i FROM InteractionLog i WHERE i.sales.id = :salesId")
    List<InteractionLog> findAllBySalesId(Long salesId);

    @Query("SELECT i FROM InteractionLog i WHERE i.sales.customer.id = :customerId")
    List<InteractionLog> findAllByCustomerId(Long customerId);

//    @Query("SELECT COUNT(i) FROM InteractionLog i WHERE i.sales.customer.id = :customerId")
//    Long countByCustomerId(Long customerId);
}
