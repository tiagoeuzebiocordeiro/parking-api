package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.exception.EntityNotFoundException;
import com.tiagocordeiro.parkingapi.exception.UniqueCpfViolationException;
import com.tiagocordeiro.parkingapi.repository.CustomerRepository;
import com.tiagocordeiro.parkingapi.repository.projection.CustomerProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(
                "Customer of id=%s not found", id
        )));
    }

    @Transactional(readOnly = true)
    public Page<CustomerProjection> findAll(Pageable pageable) {
        return customerRepository.findAllPageable(pageable);
    }
}
