package com.software.modsen.eurekaserver.through;

import com.software.modsen.eurekaserver.entities.passenger.Passenger;
import com.software.modsen.eurekaserver.entities.passenger.PassengerAccount;
import com.software.modsen.eurekaserver.entities.passenger.PassengerDto;
import com.software.modsen.eurekaserver.entities.passenger.PassengerRating;
import com.software.modsen.eurekaserver.entities.rating.Rating;
import com.software.modsen.eurekaserver.entities.rating.RatingDto;
import com.software.modsen.eurekaserver.entities.ride.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class RatingThroughTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Disabled
    void getRatingTest() {
        ResponseEntity<Rating> rating =
                testRestTemplate.getForEntity("http://localhost:8765/api/rating/1", Rating.class);

        assertThat(rating).isNotNull();
        assertThat(rating.getBody().getRatingValue()).isEqualTo(5);
        assertThat(rating.getBody().getRide().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(rating.getBody().getRide().getDriver().getName()).isEqualTo("Vadim");
        assertThat(rating.getBody().getComment()).isEqualTo("Comment here");
    }

    @Test
    @Disabled
    void saveRatingTest() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RatingDto ratingDto = new RatingDto(1L, 2,
                "Comment from test");
        HttpEntity<RatingDto> ratingDtoHttpEntity = new HttpEntity<>(ratingDto, headers);

        ResponseEntity<Rating> rating =
                testRestTemplate.exchange(
                        "http://localhost:8765/api/rating?ratingSource=DRIVER",
                        HttpMethod.POST,
                        ratingDtoHttpEntity,
                        Rating.class);

        assertThat(rating).isNotNull();
        assertThat(rating.getBody().getRatingValue()).isEqualTo(2);
        assertThat(rating.getBody().getRide().getPassenger().getName()).isEqualTo("Kirill");
        assertThat(rating.getBody().getRide().getDriver().getName()).isEqualTo("Vadim");
        assertThat(rating.getBody().getComment()).isEqualTo("Comment from test");

        Thread.sleep(1500);
        System.out.println(rating.getBody());

        ResponseEntity<PassengerRating> passengerRating =
                testRestTemplate.getForEntity("http://localhost:8765/api/passenger/rating/" +
                        rating.getBody().getRide().getPassenger().getId() + "/by-passenger", PassengerRating.class);

        assertThat(passengerRating).isNotNull();
        assertThat(passengerRating.getBody().getRatingValue()).isEqualTo(2);
        assertThat(passengerRating.getBody().getNumberOfRatings()).isEqualTo(1);
    }
}