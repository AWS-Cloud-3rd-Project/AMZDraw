package com.amzmall.project.service.customer;

import com.amzmall.project.controller.dto.customer.CustomerDTO;
import com.amzmall.project.controller.dto.customer.CustomerUpdateDTO;
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

    public CustomerDTO getCustomerByEmail(String email) {
        // 이메일을 기반으로 사용자 정보 조회
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        return optionalCustomer.map(CustomerDTO::of).orElse(null);
    }

    public boolean updateCustomer(String loggedInCustomerEmail, CustomerUpdateDTO customerUpdateDTO) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(loggedInCustomerEmail));
        Customer existingCustomer = null;
        if (optionalCustomer.isPresent()) {
            existingCustomer = optionalCustomer.get();

            existingCustomer.setUsername(customerUpdateDTO.getUsername());
            existingCustomer.setPhoneNumber(customerUpdateDTO.getPhoneNumber());
            existingCustomer.setZipCode(customerUpdateDTO.getZipCode());
            existingCustomer.setAddress(customerUpdateDTO.getAddress());
        }
        customerRepository.save(existingCustomer);
        return true; //업데이트 성공 시 true
    }
}