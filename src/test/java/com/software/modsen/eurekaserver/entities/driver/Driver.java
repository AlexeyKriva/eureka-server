package com.software.modsen.eurekaserver.entities.driver;

import com.software.modsen.eurekaserver.entities.driver.car.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Sex sex;

    private Car car;

    private boolean isDeleted;
}