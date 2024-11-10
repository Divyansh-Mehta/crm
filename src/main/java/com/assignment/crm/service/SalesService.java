package com.assignment.crm.service;

import com.assignment.crm.model.Sales;

import java.util.List;

public interface SalesService {
    List<Sales> getAllSales(Long customerId);
    Sales getSalesById(Long id);
    Sales createSales(Sales sales, Long customerId);
    boolean updateSales(Sales sales);
    boolean deleteSales(Long id);
    boolean updateStage(Long id, String stage);
    boolean closeDeal(Long id);
}
