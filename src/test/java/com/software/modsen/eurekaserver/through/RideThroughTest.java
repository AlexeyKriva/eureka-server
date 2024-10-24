package com.software.modsen.eurekaserver.through;

import com.software.modsen.eurekaserver.entities.driver.DriverAccount;
import com.software.modsen.eurekaserver.entities.passenger.*;
import com.software.modsen.eurekaserver.entities.ride.Currency;
import com.software.modsen.eurekaserver.entities.ride.Ride;
import com.software.modsen.eurekaserver.entities.ride.RideDto;
import com.software.modsen.eurekaserver.entities.ride.RideStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class RideThroughTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Disabled
    void getRideTest() {
        ResponseEntity<Ride> ride =
                testRestTemplate.getForEntity("http://localhost:8765/api/ride/1", Ride.class);

        assertThat(ride).isNotNull();
        assertThat(ride.getBody().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(ride.getBody().getDriver().getName()).isEqualTo("Vadim");
        assertThat(ride.getBody().getFromAddress()).isEqualTo("Nezavisimosty 1");
        assertThat(ride.getBody().getToAddress()).isEqualTo("Nezavisimosty 100");
        assertThat(ride.getBody().getOrderDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 15,
                12, 0));
    }

    @Test
    @Disabled
    void saveRideTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RideDto rideDto = new RideDto(1L, 1L,
                "Nezavisimosty 187", "Nezavisimosty 190", LocalDateTime.of(2024, 10,
                10, 21, 15, 10), 155F, Currency.BYN);
        HttpEntity<RideDto> rideDtoHttpEntity = new HttpEntity<>(rideDto, headers);

        ResponseEntity<Ride> ride =
                testRestTemplate.exchange(
                        "http://localhost:8765/api/ride",
                        HttpMethod.POST,
                        rideDtoHttpEntity,
                        Ride.class);

        assertThat(ride).isNotNull();
        assertThat(ride.getBody().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(ride.getBody().getDriver().getName()).isEqualTo("Vadim");
        assertThat(ride.getBody().getFromAddress()).isEqualTo("Nezavisimosty 187");
        assertThat(ride.getBody().getToAddress()).isEqualTo("Nezavisimosty 190");
        assertThat(ride.getBody().getOrderDateTime()).isEqualTo(LocalDateTime.of(2024, 10, 10,
                21, 15, 10));
    }

    @Test
    @Disabled
    void updateRideStatusToCompletedTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PassengerAccountIncreaseDto passengerAccountIncreaseDto = new PassengerAccountIncreaseDto(10000F,
                Currency.BYN);

        HttpEntity<PassengerAccountIncreaseDto> passengerAccountIncreaseDtoHttpEntity
                = new HttpEntity<>(passengerAccountIncreaseDto, headers);

        ResponseEntity<PassengerAccount> passengerAccount = testRestTemplate.exchange(
                        "http://localhost:8765/api/passenger/account/1/increase",
                        HttpMethod.PUT,
                        passengerAccountIncreaseDtoHttpEntity,
                        PassengerAccount.class);

        ResponseEntity<DriverAccount> driverAccount = testRestTemplate.getForEntity(
                "http://localhost:8765/api/driver/account/1/by-driver", DriverAccount.class);

        ResponseEntity<Ride> ride = testRestTemplate.exchange(
                "http://localhost:8765/api/ride/1/status?status=COMPLETED",
                HttpMethod.PATCH,
                null,
                Ride.class);

        assertThat(ride).isNotNull();
        assertThat(ride.getBody().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(ride.getBody().getDriver().getName()).isEqualTo("Vadim");
        assertThat(ride.getBody().getFromAddress()).isEqualTo("Nezavisimosty 1");
        assertThat(ride.getBody().getToAddress()).isEqualTo("Nezavisimosty 100");
        assertThat(ride.getBody().getRideStatus()).isEqualTo(RideStatus.COMPLETED);
        assertThat(ride.getBody().getOrderDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 15,
                12, 0));

        ResponseEntity<PassengerAccount> updatedPassengerAccount = testRestTemplate.getForEntity(
                "http://localhost:8765/api/passenger/account/1/by-passenger", PassengerAccount.class);

        ResponseEntity<DriverAccount> updatedDriverAccount = testRestTemplate.getForEntity(
                "http://localhost:8765/api/driver/account/1/by-driver", DriverAccount.class);

        assertThat(updatedPassengerAccount).isNotNull();
        assertThat(updatedDriverAccount).isNotNull();
        assertThat(updatedPassengerAccount.getBody().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(updatedDriverAccount.getBody().getDriver().getName()).isEqualTo("Vadim");
        assertThat(updatedPassengerAccount.getBody().getBalance()).isEqualTo(
                passengerAccount.getBody().getBalance() - ride.getBody().getPrice()
        );
        assertThat(updatedDriverAccount.getBody().getBalance()).isEqualTo(
                driverAccount.getBody().getBalance() + ride.getBody().getPrice() * 0.8F
        );
    }
}