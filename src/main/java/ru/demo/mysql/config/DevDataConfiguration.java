package ru.demo.mysql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.demo.mysql.api.dto.CreateCustomerRequest;
import ru.demo.mysql.api.dto.CreateOrderRequest;
import ru.demo.mysql.repository.CustomerRepository;
import ru.demo.mysql.service.CustomerService;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConditionalOnProperty(name = "app.demo-data.enabled", havingValue = "true")
public class DevDataConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DevDataConfiguration.class);

    @Bean
    CommandLineRunner seedDemoData(CustomerRepository repository, CustomerService customerService) {
        return args -> {
            if (repository.count() == 0) {
                customerService.create(new CreateCustomerRequest("Alice Johnson", "alice@example.com", "Berlin",
                        List.of(new CreateOrderRequest("Laptop", new BigDecimal("1200.00")),
                                new CreateOrderRequest("Monitor", new BigDecimal("350.00")))));
                log.info("Inserted demo customer and orders into the dev database");
            }
            log.info("Dev database currently has {} customers", repository.count());
        };
    }
}
