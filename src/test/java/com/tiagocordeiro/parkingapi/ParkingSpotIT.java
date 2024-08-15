package com.tiagocordeiro.parkingapi;

import com.tiagocordeiro.parkingapi.web.dto.ParkingSpotCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parkingspots/insert-parking-spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parkingspots/delete-parking-spots.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingSpotIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCheckIn_WithValidData_ReturnsCreated201AndLocation() {
        ParkingSpotCreateDto createDto = ParkingSpotCreateDto.builder()
                .licensePlate("WER-1111").brand("FIAT").model("PALIO 1.0").color("BLUE").customerCpf("09191773016")
                .build();

        testClient.post().uri("api/v1/parking-spots/check-in").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto).exchange().expectStatus().isCreated().expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody().jsonPath("licensePlate").isEqualTo("WER-1111")
                .jsonPath("brand").isEqualTo("FIAT").jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("BLUE").jsonPath("customerCpf").isEqualTo("09191773016")
                .jsonPath("receipt").exists().jsonPath("entryDate").exists().jsonPath("spotCode").exists();

    }

    @Test
    public void createCheckIn_WithCustomerRole_ReturnsErrorStatus403() {
        ParkingSpotCreateDto createDto = ParkingSpotCreateDto.builder()
                .licensePlate("WER-1111").brand("FIAT").model("PALIO 1.0").color("BLUE").customerCpf("09191773016")
                .build();

        testClient.post().uri("api/v1/parking-spots/check-in").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br", "123456"))
                .bodyValue(createDto).exchange().expectStatus().isCreated().expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody().jsonPath("licensePlate").isEqualTo("WER-1111")
                .jsonPath("brand").isEqualTo("FIAT").jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("BLUE").jsonPath("customerCpf").isEqualTo("09191773016")
                .jsonPath("receipt").exists().jsonPath("entryDate").exists().jsonPath("spotCode").exists();

    }

    @Test
    public void createCheckIn_WithInvalidData_ReturnsErrorStatus422() {
        ParkingSpotCreateDto createDto = ParkingSpotCreateDto.builder()
                .licensePlate("").brand("").model("").color("").customerCpf("")
                .build();

        testClient.post().uri("api/v1/parking-spots/check-in").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br", "123456"))
                .bodyValue(createDto).exchange().expectStatus().isEqualTo(422).expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody().jsonPath("licensePlate").isEqualTo("WER-1111")
                .jsonPath("brand").isEqualTo("FIAT").jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("BLUE").jsonPath("customerCpf").isEqualTo("09191773016")
                .jsonPath("receipt").exists().jsonPath("entryDate").exists().jsonPath("spotCode").exists();

    }

    @Test
    public void createCheckIn_WithNonExistentCpf_ReturnsErrorStatus404() {
        ParkingSpotCreateDto createDto = ParkingSpotCreateDto.builder()
                .licensePlate("WER-1111").brand("FIAT").model("PALIO 1.0").color("BLUE").customerCpf("07179000099")
                .build();

        testClient.post().uri("api/v1/parking-spots/check-in").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto).exchange().expectStatus().isNotFound().expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody().jsonPath("licensePlate").isEqualTo("WER-1111")
                .jsonPath("brand").isEqualTo("FIAT").jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("BLUE").jsonPath("customerCpf").isEqualTo("09191773016")
                .jsonPath("receipt").exists().jsonPath("entryDate").exists().jsonPath("spotCode").exists();

    }

}
