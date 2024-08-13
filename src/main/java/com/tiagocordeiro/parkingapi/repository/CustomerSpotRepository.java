package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.CustomerSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSpotRepository extends JpaRepository<CustomerSpot, Long> {
}
