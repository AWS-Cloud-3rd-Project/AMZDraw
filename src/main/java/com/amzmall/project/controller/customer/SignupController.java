package com.amzmall.project.controller.customer;

import jakarta.servlet.http.HttpSession;
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

    //회원 가입 페이지
    @GetMapping(value = "/customer/signup")
    public String signupForm() {
        return "/customer/signup";
    }
    //회원가입 처리
    @PostMapping(value = "/customer/signup")
    public String signup(CustomerRegisterDTO customerRegisterDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info(">>> 회원 가입 정보: {}", customerRegisterDTO);

        customerService.validateDuplicatedCustomer(customerRegisterDTO.getEmail());

        Customer customer = Customer.createCustomer(customerRegisterDTO, passwordEncoder);
        customerService.save(customer);

        // 세션에 사용자 정보 추가 (예: 이메일)
        session.setAttribute("customerEmail", customerRegisterDTO.getEmail());

        redirectAttributes.addAttribute("customer", customerRegisterDTO.getEmail());
        return "redirect:/customer/signup-success";
    }
    //회원가입 성공 시
    @GetMapping(value = "/customer/signup-success")
    public String signupSuccess(HttpSession session, RedirectAttributes redirectAttributes) {
        // 세션에서 사용자 정보를 가져올 수 있음
        String customerEmail = (String) session.getAttribute("customerEmail");

        if (customerEmail != null) {
            log.info(">>> 회원 가입 성공, {}", customerEmail);
            return "/customer/signup-success";
        } else {
            // 사용자 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/customer/login";
        }
    }
}

