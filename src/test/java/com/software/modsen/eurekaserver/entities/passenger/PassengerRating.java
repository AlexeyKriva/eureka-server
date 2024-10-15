package com.software.modsen.eurekaserver.entities.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRating {
    private long id;

    private Passenger passenger;

    private Float ratingValue;

    private Integer numberOfRatings;
}