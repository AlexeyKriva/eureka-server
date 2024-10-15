package com.software.modsen.eurekaserver.entities.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.modsen.eurekaserver.entities.ride.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PassengerAccountIncreaseDto {
    @JsonProperty("balance")
    private Float balance;

    @JsonProperty("currency")
    private Currency currency;
}