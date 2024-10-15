package com.software.modsen.eurekaserver.entities.rating;

import com.software.modsen.eurekaserver.entities.ride.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
    private long id;

    private Ride ride;

    private Integer ratingValue;

    private String comment;
}