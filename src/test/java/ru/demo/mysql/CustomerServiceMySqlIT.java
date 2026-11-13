package ru.demo.mysql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.demo.mysql.api.dto.CreateCustomerRequest;
import ru.demo.mysql.api.dto.CreateOrderRequest;
import ru.demo.mysql.api.dto.CustomerResponse;
import ru.demo.mysql.service.CustomerService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class CustomerServiceMySqlIT {
    @Container
    @ServiceConnection
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("customer_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CustomerService customerService;

    @Test
    void persistsCustomerAndOneToManyOrdersInRealMysql() {
        CustomerResponse created = customerService.create(new CreateCustomerRequest(
                "Bob Smith", "bob@example.com", "Paris",
                List.of(new CreateOrderRequest("Keyboard", new BigDecimal("99.99")),
                        new CreateOrderRequest("Mouse", new BigDecimal("24.90")))));

        assertThat(created.id()).isNotNull();
        assertThat(created.orders()).hasSize(2);
        assertThat(created.orders()).extracting("product")
                .containsExactlyInAnyOrder("Keyboard", "Mouse");

        CustomerResponse loaded = customerService.findById(created.id());
        assertThat(loaded.email()).isEqualTo("bob@example.com");
        assertThat(loaded.orders()).hasSize(2);
    }
}
