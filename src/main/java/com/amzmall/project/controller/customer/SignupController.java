package com.amzmall.project.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.amzmall.project.controller.dto.customer.CustomerRegisterDTO;
import com.amzmall.project.domain.customer.Customer;
import com.amzmall.project.service.customer.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignupController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입 폼
    @GetMapping(value = "/customer/signup")
    public String signupForm() {
        return "/customer/signup";
    }

    // 회원 가입
    @PostMapping(value = "/customer/signup")
    public String signup(CustomerRegisterDTO customerRegisterDTO, RedirectAttributes redirectAttributes) {
        log.info(">>> 회원 가입 정보: {}", customerRegisterDTO);

        customerService.validateDuplicatedCustomer(customerRegisterDTO.getEmail());

        Customer generalCustomer = Customer.createCustomer(customerRegisterDTO, passwordEncoder);
        customerService.save(generalCustomer);

        redirectAttributes.addAttribute("customer", customerRegisterDTO.getEmail());
        return "redirect:/customer/signup-success";
    }

    @GetMapping(value = "/customer/signup-success")
    public String signupSuccess(RedirectAttributes redirectAttributes) {
        log.info(">>> 회원 가입 성공, {}", redirectAttributes.getAttribute("customer"));
        return "/customer/signup-success";
    }

}
