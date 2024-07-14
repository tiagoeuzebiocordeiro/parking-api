package com.tiagocordeiro.parkingapi.repository;

import com.tiagocordeiro.parkingapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
