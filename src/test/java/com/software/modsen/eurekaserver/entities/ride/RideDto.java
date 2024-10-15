package com.software.modsen.eurekaserver.entities.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class RideDto {
    @JsonProperty("passenger_id")
    private Long passengerId;

    @JsonProperty("driver_id")
    private Long driverId;

    @JsonProperty("from_address")
    private String fromAddress;

    @JsonProperty("to_address")
    private String toAddress;

    @JsonProperty("order_date_time")
    private LocalDateTime orderDateTime;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("currency")
    private Currency currency;
}