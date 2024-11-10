package com.assignment.crm.service;

import com.assignment.crm.model.InteractionLog;

import java.util.List;

public interface InteractionLogService {
    public List<InteractionLog> getAllInteractionLogsOfASales(Long salesId);
    public List<InteractionLog> getAllInteractionLogsOfACustomer(Long customerId);
    public InteractionLog getInteractionLogById(Long id);
    public InteractionLog saveInteractionLog(InteractionLog log, Long salesId);
    public boolean updateInteractionLog(InteractionLog log);
    public boolean deleteInteractionLog(Long id);
    public boolean addNotes(String notes, Long id);
}
