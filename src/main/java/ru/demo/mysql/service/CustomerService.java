package ru.demo.mysql.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.mysql.api.dto.CreateCustomerRequest;
import ru.demo.mysql.api.dto.CreateOrderRequest;
import ru.demo.mysql.api.dto.CustomerResponse;
import ru.demo.mysql.api.dto.OrderResponse;
import ru.demo.mysql.domain.Customer;
import ru.demo.mysql.domain.CustomerOrder;
import ru.demo.mysql.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new CustomerAlreadyExistsException(request.email());
        }

        Customer customer = new Customer(request.name().trim(), request.email().trim().toLowerCase(), request.city());
        for (CreateOrderRequest order : request.orders()) {
            customer.addOrder(new CustomerOrder(order.product().trim(), order.total()));
        }
        try {
            return toResponse(customerRepository.saveAndFlush(customer));
        } catch (DataIntegrityViolationException exception) {
            throw new CustomerAlreadyExistsException(request.email());
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAllByOrderByIdAsc().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return customerRepository.findWithOrdersById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private CustomerResponse toResponse(Customer customer) {
        List<OrderResponse> orders = customer.getOrders().stream()
                .map(order -> new OrderResponse(order.getId(), order.getProduct(), order.getTotal(), order.getCreatedAt()))
                .toList();
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getCity(), customer.getCreatedAt(), orders);
    }
}
