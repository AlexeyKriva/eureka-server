package com.software.modsen.eurekaserver.entities.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRating {
    private long id;

    private Driver driver;

    private Float ratingValue;

    private Integer numberOfRatings;
}