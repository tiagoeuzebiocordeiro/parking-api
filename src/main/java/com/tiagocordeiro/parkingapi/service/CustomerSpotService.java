package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.CustomerSpot;
import com.tiagocordeiro.parkingapi.repository.CustomerSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSpotService {

    @Autowired
    private CustomerSpotRepository customerSpotRepository;

    @Transactional
    public CustomerSpot save(CustomerSpot obj) {
        return customerSpotRepository.save(obj);
    }

}
