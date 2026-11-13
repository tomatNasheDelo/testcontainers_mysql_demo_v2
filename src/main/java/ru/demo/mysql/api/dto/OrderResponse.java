package ru.demo.mysql.api.dto;

import java.time.Instant;

public record OrderResponse(Long id, String product, java.math.BigDecimal total, Instant createdAt) { }
