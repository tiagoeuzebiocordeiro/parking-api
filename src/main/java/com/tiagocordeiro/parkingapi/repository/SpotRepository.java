package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    Optional<Spot> findByCode(String code);
}
