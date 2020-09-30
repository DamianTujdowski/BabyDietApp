package com.dietator.diet.service;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.repository.ConsumptionTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumptionTimeService {
    private final ConsumptionTimeRepository consumptionTimeRepository;

    public ConsumptionTime save(ConsumptionTime consumptionTime) {
        return consumptionTimeRepository.save(consumptionTime);
    }

    public ConsumptionTime findConsumptionTimeById(long id) {
        return consumptionTimeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ConsumptionTime.class, id));
    }

    public List<ConsumptionTime> findAll() {
        return consumptionTimeRepository.findAll();
    }
}
