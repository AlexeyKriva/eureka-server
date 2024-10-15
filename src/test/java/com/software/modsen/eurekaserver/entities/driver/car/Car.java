package com.software.modsen.eurekaserver.entities.driver.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private long id;

    private CarColor color;

    private CarBrand brand;

    private String carNumber;

    private boolean isDeleted;
}