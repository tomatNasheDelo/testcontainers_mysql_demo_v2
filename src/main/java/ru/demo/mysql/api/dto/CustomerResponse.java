package ru.demo.mysql.api.dto;

import java.time.Instant;
import java.util.List;

public record CustomerResponse(Long id, String name, String email, String city,
                               Instant createdAt, List<OrderResponse> orders) { }
