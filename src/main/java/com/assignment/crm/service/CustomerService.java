package com.assignment.crm.service;

import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.repository.CustomerRepository;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(Long id);
    boolean addInteractionLog(Long customerId, InteractionLog interactionLog);
    boolean removeInteractionLog(Long customerId, InteractionLog interactionLog);
}
