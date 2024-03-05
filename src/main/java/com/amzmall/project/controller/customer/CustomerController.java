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

    // 회원 정보 조회 및 구매 내역 조회
    @GetMapping(value = "/customer/mypage")
    public String viewProfile(HttpSession session, Model model) {
        // 세션에서 사용자 정보를 가져오기
        String loggedInCustomerEmail = (String) session.getAttribute("customerEmail");

        // 세션에 사용자 정보가 없으면 로그인 페이지로 리다이렉트
        if (loggedInCustomerEmail == null) {
            return "redirect:/customer/login";
        }

        // 회원 정보를 모델에 추가
        populateModelWithCustomerInfo(loggedInCustomerEmail, model);

        // 여기에서 사용자의 구매 내역 조회 로직을 추가
        // model.addAttribute("purchaseHistory", purchaseService.getPurchaseHistory(loggedInCustomerEmail));

        return "my-page"; // 구매 내역을 보여줄 뷰 페이지
    }

    // 회원 정보 수정
    @PostMapping(value = "/customer/update")
    public String updateProfile(@RequestBody CustomerUpdateDTO customerUpdateDTO, HttpSession session) {
        // 세션에서 사용자 정보를 가져오기
        String loggedInCustomerEmail = (String) session.getAttribute("customerEmail");

        // 세션에 사용자 정보가 없으면 로그인 페이지로 리다이렉트
        if (loggedInCustomerEmail == null) {
            return "redirect:/customer/login";
        }

        // 회원 정보 업데이트
        if (customerService.updateCustomer(loggedInCustomerEmail, customerUpdateDTO)) {
            return "redirect:/customer/mypage"; // 성공 시 마이페이지로 리다이렉트
        } else {
            return "redirect:/error"; // 실패 시 에러 페이지로 리다이렉트
        }
    }

    private void populateModelWithCustomerInfo(String email, Model model) {
        CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customerDTO);
    }
}
