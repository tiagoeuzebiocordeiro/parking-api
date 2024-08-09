package com.tiagocordeiro.parkingapi;

import com.tiagocordeiro.parkingapi.web.dto.SpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.SpotResponseDto;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/spots/insert-spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/spots/delete-spots.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SpotIT {

    @Autowired
    WebTestClient testClient;


    @Test
    public void createSpot_WithValidData_ReturnsLocationURIStatus201() {
        testClient.post().uri("/api/v1/spots").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "test1@mail.com", "123456"))
                .bodyValue(new SpotCreateDto("A-05", "FREE"))
                .exchange().expectStatus().isCreated().expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createSpot_WhoWasAlreadyRegisteredCode_ReturnsErrorMessage409() {
        testClient.post().uri("/api/v1/spots").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "test1@mail.com", "123456"))
                .bodyValue(new SpotCreateDto("A-01", "FREE")).exchange().expectStatus().isEqualTo(409)
                .expectBody().jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/spots");
    }

    @Test
    public void createSpot_UsingCustomerRole_ReturnsErrorMessage422() {
        testClient.post().uri("/api/v1/spots").contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "test1@mail.com", "123456"))
                .bodyValue(new SpotCreateDto("A-072", "BUSY LOL")).exchange()
                .expectStatus().isEqualTo(422).expectBody().jsonPath("method").isEqualTo("POST")
                .jsonPath("status").isEqualTo(422).jsonPath("path").isEqualTo("/api/v1/spots");
    }

    @Test
    public void findSpot_WithAnExistentCode_ReturnsSpot200() {
        testClient.get().uri("/api/v1/spots/{code}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(
                        testClient, "test1@mail.com", "123456")).exchange().expectStatus().isOk()
                .expectBody().jsonPath("id").isEqualTo("10").jsonPath("code")
                .isEqualTo("A-01").jsonPath("status").isEqualTo("FREE");
    }

    @Test
    public void findSpot_WithANotExistentCode_ReturnsErrorMessage404() {
        testClient.get().uri("/api/v1/spots/{code}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(
                        testClient, "test1@mail.com", "123456")).exchange().expectStatus().isNotFound()
                .expectBody().jsonPath("status").isEqualTo(404).jsonPath("method")
                .isEqualTo("GET").jsonPath("path").isEqualTo("/api/v1/spots/A-10");
    }

}
