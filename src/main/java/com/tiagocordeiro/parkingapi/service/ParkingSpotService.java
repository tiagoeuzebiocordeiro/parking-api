package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.entity.CustomerSpot;
import com.tiagocordeiro.parkingapi.entity.Spot;
import com.tiagocordeiro.parkingapi.utils.ParkingSpotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ParkingSpotService {

    @Autowired
    private CustomerSpotService customerSpotService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private  SpotService spotService;

    @Transactional
    public CustomerSpot checkIn(CustomerSpot customerSpot) {
        Customer customer = customerService.findByCpf(customerSpot.getCustomer().getCpf());
        customerSpot.setCustomer(customer);

        Spot spot = spotService.findAFreeSpot();
        spot.setStatus(Spot.SpotStatus.OCCUPIED);

        customerSpot.setSpot(spot);
        customerSpot.setEntryDate(LocalDateTime.now());
        customerSpot.setReceipt(ParkingSpotUtils.generateReceipt());

        return customerSpotService.save(customerSpot);
    }

}
