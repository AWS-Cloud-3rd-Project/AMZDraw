package com.amzmall.project.service.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.amzmall.project.domain.customer.Customer;
import com.amzmall.project.exception.DuplicatedEmailException;
import com.amzmall.project.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void validateDuplicatedCustomer(String email) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        if (optionalCustomer.isPresent()) {
            throw new DuplicatedEmailException("중복 이메일입니다.");
        }
    }
}