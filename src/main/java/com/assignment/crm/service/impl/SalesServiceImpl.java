package com.assignment.crm.service.impl;

import com.assignment.crm.model.Customer;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.SalesRepository;
import com.assignment.crm.service.CustomerService;
import com.assignment.crm.service.SalesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final CustomerService customerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);

    public SalesServiceImpl(SalesRepository salesRepository, CustomerService customerService) {
        this.salesRepository = salesRepository;
        this.customerService = customerService;
    }

    @Override
    public List<Sales> getAllSales(Long customerId) {
        return salesRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Sales getSalesById(Long id) {
        Optional<Sales> salesOptional = salesRepository.findById(id);
        return salesOptional.orElse(null);
    }

    @Override
    public Sales createSales(Sales sales, Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        sales.setCustomer(customer);
        if (sales.getCreatedAt() == null) {
            sales.setCreatedAt(LocalDateTime.now());
        }
        return salesRepository.save(sales);
    }

    @Override
    public boolean updateSales(Sales sales) {
        Optional<Sales> salesOptional = salesRepository.findById(sales.getId());
        if (salesOptional.isPresent()) {
            Sales updateSales = salesOptional.get();
            updateSales.setId(sales.getId());
            updateSales.setDealSize(sales.getDealSize());
            updateSales.setProbabilityOfClosing(sales.getProbabilityOfClosing());
            salesRepository.save(updateSales);
            return true;
        }
        LOGGER.error("Sales update with id {} failed", sales.getId());
        return false;
    }

    @Override
    public boolean deleteSales(Long id) {
        if (salesRepository.existsById(id)) {
            salesRepository.deleteById(id);
            return true;
        }
        LOGGER.error("Sales deletion with id {} failed", id);
        return false;
    }

    @Override
    public boolean updateStage(Long id, String stage) {
        Optional<Sales> optionalSales = salesRepository.findById(id);
        if (optionalSales.isPresent()) {
            Sales sales = optionalSales.get();
            sales.setStage(stage);
            salesRepository.save(sales);
            return true;
        }
        LOGGER.error("Stage update for sales with id {} failed", id);
        return false;
    }

    @Override
    public boolean closeDeal(Long id) {
        Optional<Sales> salesOptional = salesRepository.findById(id);
        if (salesOptional.isPresent()) {
            Sales sales = salesOptional.get();
            sales.setClosingDate(LocalDateTime.now());
            salesRepository.save(sales);
            return true;
        }
        LOGGER.error("Deal closing for sales with id {} failed", id);
        return false;
    }
}
