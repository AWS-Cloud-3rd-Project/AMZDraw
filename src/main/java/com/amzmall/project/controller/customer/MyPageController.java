package com.amzmall.project.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    // 세션을 이용한 구매 내역 조회
    @GetMapping(value = {"/customer/mypage", "/customer/mypage/"})
    public String myPage(HttpSession session, Model model) {
        // 세션에서 사용자 정보를 가져오기
        String loggedInCustomerEmail = (String) session.getAttribute("customerEmail");

        // 세션에 사용자 정보가 없으면 로그인 페이지로 리다이렉트 또는 다른 처리 가능
        if (loggedInCustomerEmail == null) {
            // 예시: 로그인 페이지로 리다이렉트
            return "redirect:/customer/login";
        }

        // 여기에서 사용자의 구매 내역 조회 로직을 추가
        // model.addAttribute("purchaseHistory", purchaseService.getPurchaseHistory(loggedInCustomerEmail));

        return "my-page"; // 구매 내역을 보여줄 뷰 페이
    }
}
