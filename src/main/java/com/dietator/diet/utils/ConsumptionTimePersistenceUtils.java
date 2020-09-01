package com.dietator.diet.utils;

import com.dietator.diet.domain.ConsumptionTime;

import java.util.Set;

public class ConsumptionTimePersistenceUtils {

    private ConsumptionTimePersistenceUtils() {
    }

    public static Set<ConsumptionTime> filterNewConsumptionTimes(Set<ConsumptionTime> consumptionTimesFromUser, Set<ConsumptionTime> consumptionTimesFromDb) {
        consumptionTimesFromUser.removeAll(consumptionTimesFromDb);
        return consumptionTimesFromUser;
    }
}
