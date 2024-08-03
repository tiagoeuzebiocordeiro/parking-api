package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.exception.UniqueCpfViolationException;
import com.tiagocordeiro.parkingapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer obj) {
        try {
            return customerRepository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueCpfViolationException(String.format
                    ("This Cpf '%s' cannot be registered because it already exists in the system.", obj.getCpf()));
        }
    }


}
