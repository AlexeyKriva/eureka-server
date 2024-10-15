package com.software.modsen.eurekaserver.entities.passenger;

import com.software.modsen.eurekaserver.entities.ride.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerAccount {
    private long id;

    private Passenger passenger;

    private Float balance;

    private Currency currency;
}
