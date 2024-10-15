package com.software.modsen.eurekaserver.entities.driver;

import com.software.modsen.eurekaserver.entities.ride.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverAccount {
    private long id;

    private Driver driver;

    private Float balance;

    private Currency currency;
}