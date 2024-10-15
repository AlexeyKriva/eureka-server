package com.software.modsen.eurekaserver.through;

import com.software.modsen.eurekaserver.entities.passenger.Passenger;
import com.software.modsen.eurekaserver.entities.passenger.PassengerAccount;
import com.software.modsen.eurekaserver.entities.passenger.PassengerDto;
import com.software.modsen.eurekaserver.entities.passenger.PassengerRating;
import com.software.modsen.eurekaserver.entities.ride.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerThroughTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Disabled
    void getPassengerTest() {
        ResponseEntity<Passenger> passenger =
                testRestTemplate.getForEntity("http://localhost:8765/api/passenger/1", Passenger.class);

        assertThat(passenger).isNotNull();
        assertThat(passenger.getBody().getName()).isEqualTo("Kirill");
        assertThat(passenger.getBody().getEmail()).isEqualTo("kirill@gmail.com");
        assertThat(passenger.getBody().getPhoneNumber()).isEqualTo("+375293878555");
    }

    @Test
    @Disabled
    void savePassengerTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PassengerDto passengerDto = new PassengerDto("Ignat", "ignat@gmail.com",
                "+375293337771");
        HttpEntity<PassengerDto> passengerDtoHttpEntity = new HttpEntity<>(passengerDto, headers);

        ResponseEntity<Passenger> passenger =
                testRestTemplate.exchange(
                        "http://localhost:8765/api/passenger",
                        HttpMethod.POST,
                        passengerDtoHttpEntity,
                        Passenger.class);

        assertThat(passenger).isNotNull();
        assertThat(passenger.getBody().getName()).isEqualTo("Ignat");
        assertThat(passenger.getBody().getEmail()).isEqualTo("ignat@gmail.com");
        assertThat(passenger.getBody().getPhoneNumber()).isEqualTo("+375293337771");

        ResponseEntity<PassengerRating> passengerRating =
                testRestTemplate.getForEntity("http://localhost:8765/api/passenger/rating/" +
                        passenger.getBody().getId() + "/by-passenger", PassengerRating.class);

        assertThat(passengerRating).isNotNull();
        assertThat(passengerRating.getBody().getRatingValue()).isEqualTo(0);
        assertThat(passengerRating.getBody().getNumberOfRatings()).isEqualTo(0);

        ResponseEntity<PassengerAccount> passengerAccount =
                testRestTemplate.getForEntity("http://localhost:8765/api/passenger/account/" +
                        passenger.getBody().getId() + "/by-passenger", PassengerAccount.class);

        assertThat(passengerAccount).isNotNull();
        assertThat(passengerAccount.getBody().getBalance()).isEqualTo(0);
        assertThat(passengerAccount.getBody().getCurrency()).isEqualTo(Currency.BYN);
    }
}