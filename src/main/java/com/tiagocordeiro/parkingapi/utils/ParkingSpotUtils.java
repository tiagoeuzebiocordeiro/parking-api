package com.tiagocordeiro.parkingapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
* GENERATE RECEIPT, CALC DISCOUNT AND PARKING SPOT VALUE
* */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotUtils {

    public static String generateReceipt() {
        LocalDateTime date = LocalDateTime.now();
        return date.toString().substring(0,19).replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }

}
