package com.software.modsen.eurekaserver.entities.driver.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class CarDto {
    @JsonProperty("color")
    private CarColor color;

    @JsonProperty("brand")
    private CarBrand brand;

    @JsonProperty("car_number")
    private String carNumber;
}
