# Spring Boot + MySQL + Flyway + Testcontainers

Production-style учебный проект с JPA/Hibernate, One-to-Many связью, DTO, валидацией, профилями `dev`/`test` и миграциями Flyway.

- Локальное приложение работает с MySQL из Docker Compose.
- Интеграционный тест стартует отдельную MySQL через Testcontainers и проверяет сохранение Customer вместе с Orders.
- API не отдаёт JPA entities напрямую: контроллеры используют DTO.
- `start.txt` содержит объяснение слоёв и все команды для запуска.
