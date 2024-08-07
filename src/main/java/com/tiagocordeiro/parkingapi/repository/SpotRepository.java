package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
