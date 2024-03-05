package com.amzmall.project.controller.customer;

import com.amzmall.project.controller.dto.customer.CustomerDTO;
import com.amzmall.project.controller.dto.customer.CustomerUpdateDTO;
import com.amzmall.project.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    // 회원 정보 조회
    @GetMapping(value = "/customer/myprofile")
    public String viewProfile(HttpSession session, Model model) {
        String loggedInCustomerEmail = getLoggedInCustomerEmail(session);
        if (loggedInCustomerEmail == null) {
            return "redirect:/customer/login";
        }

        populateModelWithCustomerInfo(loggedInCustomerEmail, model);
        return "customer/profile";
    }

    // 회원 수정
    @PostMapping(value = "/customer/update")
    public String updateProfile(@RequestBody CustomerUpdateDTO customerUpdateDTO, HttpSession session) {
        String loggedInCustomerEmail = getLoggedInCustomerEmail(session);
        if (loggedInCustomerEmail == null) {
            return "redirect:/customer/login";
        }

        if (customerService.updateCustomer(loggedInCustomerEmail, customerUpdateDTO)) {
            return "redirect:/customer/myprofile";
        } else {
            return "redirect:/error";
        }
    }

    private String getLoggedInCustomerEmail(HttpSession session) {
        return (String) session.getAttribute("customerEmail");
    }

    private void populateModelWithCustomerInfo(String email, Model model) {
        CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customerDTO);
    }
}

