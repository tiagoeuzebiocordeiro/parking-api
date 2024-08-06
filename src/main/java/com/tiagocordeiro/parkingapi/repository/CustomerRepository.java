package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.repository.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM TB_CUSTOMERS", nativeQuery = true)
    Page<CustomerProjection> findAllPageable(Pageable pageable);

    Customer findByUserId(Long id);
}
