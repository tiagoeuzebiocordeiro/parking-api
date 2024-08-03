package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
