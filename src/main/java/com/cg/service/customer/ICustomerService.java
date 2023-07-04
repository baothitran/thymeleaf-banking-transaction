package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAllByDeletedIsFalse();
    List<Customer> findAllByIdNot(Long id);
    Boolean existsByEmail(String email);
}