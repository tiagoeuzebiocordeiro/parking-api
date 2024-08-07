package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.Spot;
import com.tiagocordeiro.parkingapi.exception.EntityNotFoundException;
import com.tiagocordeiro.parkingapi.exception.UniqueSpotCodeViolationException;
import com.tiagocordeiro.parkingapi.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;

    @Transactional
    public Spot save(Spot obj) {
        try {
            return spotRepository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueSpotCodeViolationException(String.format
                    ("Spot's code=%s already registered in database.", obj.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Spot findByCode(String code) {
        return spotRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(String.format(
                "Spot with code=%s not found", code
        )));
    }

}
