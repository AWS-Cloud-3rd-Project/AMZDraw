package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.customer.Customer;
import com.amzmall.project.admin.exception.NotFoundCustomerException;
import com.amzmall.project.admin.service.CustomerService;
import com.amzmall.project.admin.service.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/member_admin/")
public class CustomerController {

    private final CustomerService customerService;

    private static final String ATTRIBUTE_KEY_CUSTOMERS = "customers";
    private static final String MENU_KEY = "customers";
    private static final String VIEW_CUSTOMERS = "member_admin/customers";
    private static final String VIEW_CUSTOMER_DETAIL = "member_admin/customers/customer-detail";

    @GetMapping
    public String index(Model model) {
        List<CustomerDTO> customerDTOs = mapCustomersToDTO(customerService.findTop100ByActiveCustomer());
        addCommonModelAttributes(model);
        model.addAttribute(ATTRIBUTE_KEY_CUSTOMERS, customerDTOs);
        return VIEW_CUSTOMERS;
    }

    @GetMapping("/customer-detail")
    public String detail(@RequestParam Long customerId, Model model) {
        Customer customer = getCustomerById(customerId);
        addCommonModelAttributes(model);
        model.addAttribute("customer", customer);
        return VIEW_CUSTOMER_DETAIL;
    }

    private List<CustomerDTO> mapCustomersToDTO(List<Customer> customers) {
        return customers.stream()
                .map(customer -> CustomerDTO.of(
                        customer.getCustomerId(),
                        customer.getCustomerName(),
                        customer.getPhoneNumber(),
                        customer.getAddress(),
                        customer.getStatus(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    private Customer getCustomerById(Long customerId) {
        return customerService.findById(customerId)
                .orElseThrow(() -> new NotFoundCustomerException("고객 정보를 찾을 수 없습니다. ID: " + customerId));
    }

    private void addCommonModelAttributes(Model model) {
        model.addAttribute("menuId", MENU_KEY);
    }
}
