package com.software.modsen.eurekaserver.entities.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RatingDto {
    @JsonProperty("ride_id")
    private Long rideId;

    @JsonProperty("rating_value")
    private Integer ratingValue;

    @JsonProperty("comment")
    private String comment;
}