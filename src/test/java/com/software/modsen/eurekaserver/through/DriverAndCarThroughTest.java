package com.software.modsen.eurekaserver.through;

import com.software.modsen.eurekaserver.entities.driver.*;
import com.software.modsen.eurekaserver.entities.driver.car.Car;
import com.software.modsen.eurekaserver.entities.driver.car.CarBrand;
import com.software.modsen.eurekaserver.entities.driver.car.CarColor;
import com.software.modsen.eurekaserver.entities.driver.car.CarDto;
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
public class DriverAndCarThroughTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Disabled
    void getCarTest() {
        ResponseEntity<Car> car =
                testRestTemplate.getForEntity("http://localhost:8765/api/car/1", Car.class);

        assertThat(car).isNotNull();
        assertThat(car.getBody().getColor()).isEqualTo(CarColor.WHITE);
        assertThat(car.getBody().getBrand()).isEqualTo(CarBrand.BENTLEY);
        assertThat(car.getBody().getCarNumber()).isEqualTo("1234AA-1");
    }

    @Test
    @Disabled
    void getDriverTest() {
        ResponseEntity<Driver> driver =
                testRestTemplate.getForEntity("http://localhost:8765/api/driver/1", Driver.class);

        assertThat(driver).isNotNull();
        assertThat(driver.getBody().getName()).isEqualTo("Vadim");
        assertThat(driver.getBody().getEmail()).isEqualTo("vadim@gmail.com");
        assertThat(driver.getBody().getPhoneNumber()).isEqualTo("+375293878333");
    }

    @Test
    @Disabled
    void saveCarTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CarDto carDto = new CarDto(CarColor.BLUE, CarBrand.JAGUAR,
                "1234AB-1");
        HttpEntity<CarDto> carDtoHttpEntity = new HttpEntity<>(carDto, headers);

        ResponseEntity<Car> car =
                testRestTemplate.exchange(
                        "http://localhost:8765/api/car",
                        HttpMethod.POST,
                        carDtoHttpEntity,
                        Car.class);

        assertThat(car).isNotNull();
        assertThat(car.getBody().getColor()).isEqualTo(CarColor.BLUE);
        assertThat(car.getBody().getBrand()).isEqualTo(CarBrand.JAGUAR);
        assertThat(car.getBody().getCarNumber()).isEqualTo("1234AB-1");
    }

    @Test
    @Disabled
    void saveDriverTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DriverDto driverDto = new DriverDto("Vova", "vova@gmail.com",
                "+375293337773", Sex.MALE, 2L);
        HttpEntity<DriverDto> driverDtoHttpEntity = new HttpEntity<>(driverDto, headers);

        ResponseEntity<Driver> driver =
                testRestTemplate.exchange(
                        "http://localhost:8765/api/driver",
                        HttpMethod.POST,
                        driverDtoHttpEntity,
                        Driver.class);

        assertThat(driver).isNotNull();
        assertThat(driver.getBody().getName()).isEqualTo("Vova");
        assertThat(driver.getBody().getEmail()).isEqualTo("vova@gmail.com");
        assertThat(driver.getBody().getPhoneNumber()).isEqualTo("+375293337773");
        assertThat(driver.getBody().getCar().getBrand()).isEqualTo(CarBrand.AUDI);

        ResponseEntity<DriverRating> driverRating =
                testRestTemplate.getForEntity("http://localhost:8765/api/driver/rating/" +
                        driver.getBody().getId() + "/by-driver", DriverRating.class);

        assertThat(driverRating).isNotNull();
        assertThat(driverRating.getBody().getRatingValue()).isEqualTo(0);
        assertThat(driverRating.getBody().getNumberOfRatings()).isEqualTo(0);

        ResponseEntity<DriverAccount> driverAccount =
                testRestTemplate.getForEntity("http://localhost:8765/api/driver/account/" +
                        driver.getBody().getId() + "/by-driver", DriverAccount.class);

        assertThat(driverAccount).isNotNull();
        assertThat(driverAccount.getBody().getBalance()).isEqualTo(0);
        assertThat(driverAccount.getBody().getCurrency()).isEqualTo(Currency.BYN);
    }
}