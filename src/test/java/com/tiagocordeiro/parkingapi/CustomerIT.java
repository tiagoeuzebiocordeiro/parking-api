package com.tiagocordeiro.parkingapi;

import com.tiagocordeiro.parkingapi.web.dto.CustomerCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.CustomerResponseDto;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/insert-customers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/delete-customers.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCustomer_WithValidData_ReturnsCustomerWith201HttpStatusCode() {
        CustomerResponseDto responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test4@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Marcondes", "65866701014"))
                .exchange().expectStatus().isCreated().expectBody(CustomerResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobias Marcondes");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("65866701014");
    }

    @Test
    public void createCustomer_WithCpfAlreadyRegisteredInTheSystem_ReturnsErrorMessage_WithStatus409() {
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test4@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Marcondes", "19273528007"))
                .exchange().expectStatus().isEqualTo(409).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }
    @Test
    public void createCustomer_WithInvalidData_ReturnsErrorMessage_WithStatus422() {
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test4@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("", ""))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test4@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Bob", "00000000000"))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test4@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Bob", "192.735.280-07"))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createCustomer_WithNotAuthorizedUserRole_ReturnsErrorMessage_WithStatus403() {
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,
                        "test1@mail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Marcondes", "19273528007"))
                .exchange().expectStatus().isEqualTo(403).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

}
