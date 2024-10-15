package com.software.modsen.eurekaserver.entities.ride;

import com.software.modsen.eurekaserver.entities.driver.Driver;
import com.software.modsen.eurekaserver.entities.passenger.Passenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride {
    private long id;

    private Passenger passenger;

    private Driver driver;

    private String fromAddress;

    private String toAddress;

    private RideStatus rideStatus;

    private LocalDateTime orderDateTime;

    private Float price;

    private Currency currency;
}