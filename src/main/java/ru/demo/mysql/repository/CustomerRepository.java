package ru.demo.mysql.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.mysql.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "orders")
    List<Customer> findAllByOrderByIdAsc();

    @EntityGraph(attributePaths = "orders")
    Optional<Customer> findWithOrdersById(Long id);
}
