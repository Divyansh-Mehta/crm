package com.assignment.crm.service.impl;


import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.repository.CustomerRepository;
import com.assignment.crm.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElse(null);
    }

    @Override
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer){
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());
        if (customerOptional.isPresent()){
            Customer updateCustomer = customerOptional.get();
            updateCustomer.setId(customer.getId());
            updateCustomer.setName(customer.getName());
            updateCustomer.setEmail(customer.getEmail());
            updateCustomer.setPhone(customer.getPhone());
            customerRepository.save(updateCustomer);
            return true;
        }
        LOGGER.error("Customer update with id {} failed", customer.getId());
        return false;
    }

    @Override
    public boolean deleteCustomer(Long id){
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        LOGGER.error("Customer delete with id {} failed", id);
        return false;
    }

    @Override
    public boolean addInteractionLog(Long customerId, InteractionLog interactionLog){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer.getInteractionLogs().add(interactionLog);
            customerRepository.save(customer);
            return true;
        }
        LOGGER.error("adding interaction log to customer with id {} failed", customerId);
        return false;
    }

    @Override
    public boolean removeInteractionLog(Long customerId, InteractionLog interactionLog){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer.getInteractionLogs().remove(interactionLog);
            customerRepository.save(customer);
            return true;
        }
        LOGGER.error("Removing interaction log with id {} to customer with id {} failed", interactionLog.getId(), customerId);
        return false;
    }
}