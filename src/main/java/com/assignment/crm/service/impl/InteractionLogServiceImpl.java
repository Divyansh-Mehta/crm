package com.assignment.crm.service.impl;

import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.InteractionLogRepository;
import com.assignment.crm.service.CustomerService;
import com.assignment.crm.service.InteractionLogService;
import com.assignment.crm.service.SalesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionLogServiceImpl implements InteractionLogService {
    private final InteractionLogRepository interactionLogRepository;
    private final CustomerService customerService;
    private final SalesService salesService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionLogServiceImpl.class);

    public InteractionLogServiceImpl(InteractionLogRepository interactionLogRepository, CustomerService customerService, SalesService salesService) {
        this.interactionLogRepository = interactionLogRepository;
        this.customerService = customerService;
        this.salesService = salesService;
    }

    @Override
    public List<InteractionLog> getAllInteractionLogsOfASales(Long salesId) {
        return interactionLogRepository.findAllBySalesId(salesId);
    }

    @Override
    public List<InteractionLog> getAllInteractionLogsOfACustomer(Long customerId) {
        return interactionLogRepository.findAllByCustomerId(customerId);
    }

    @Override
    public InteractionLog getInteractionLogById(Long id) {
        Optional<InteractionLog> interactionLogOptional = interactionLogRepository.findById(id);
        return interactionLogOptional.orElse(null);
    }

    @Override
    public InteractionLog saveInteractionLog(InteractionLog log, Long salesId) {
        Sales sales = salesService.getSalesById(salesId);
        log.setSales(sales);
        Long customerId = sales.getCustomer().getId();
        InteractionLog savedLog = interactionLogRepository.save(log);
        customerService.addInteractionLog(customerId, savedLog);
        return savedLog;
    }

    @Override
    public boolean updateInteractionLog(InteractionLog log) {
        Optional<InteractionLog> interactionLogOptional = interactionLogRepository.findById(log.getId());
        if (interactionLogOptional.isPresent()){
            InteractionLog updateInteractionLog = interactionLogOptional.get();
            if (log.getInteractionDate() != null){
                updateInteractionLog.setInteractionDate(log.getInteractionDate());
            }
            if (log.getType() != null){
                updateInteractionLog.setType(log.getType());
            }
            if (log.getNotes() != null){
                updateInteractionLog.setNotes(log.getNotes());
            }
            interactionLogRepository.save(updateInteractionLog);
            return true;
        }
        LOGGER.error("Updating interaction log with id {} failed", log.getId());
        return false;
    }

    @Override
    public boolean deleteInteractionLog(Long id) {
        Optional<InteractionLog> interactionLogOptional = interactionLogRepository.findById(id);
        if (interactionLogOptional.isPresent()){
            InteractionLog interactionLog = interactionLogOptional.get();
            customerService.removeInteractionLog(interactionLog.getSales().getCustomer().getId(), interactionLog);
            interactionLogRepository.deleteById(id);
            return true;
        }
        LOGGER.error("Deleting interaction log with id {} failed", id);
        return false;
    }

    @Override
    public boolean addNotes(String notes, Long id) {
        Optional<InteractionLog> interactionLogOptional = interactionLogRepository.findById(id);
        if (interactionLogOptional.isPresent()){
            InteractionLog log = interactionLogOptional.get();
            log.setNotes(notes);
            interactionLogRepository.save(log);
            return true;
        }
        LOGGER.error("Adding notes to interaction logs with id {} failed", id);
        return false;
    }
}
