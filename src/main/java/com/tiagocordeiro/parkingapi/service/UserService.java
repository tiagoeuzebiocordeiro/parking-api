package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

}
