package com.amzmall.project.controller.customer;

import com.amzmall.project.controller.dto.customer.CustomerDTO;
import com.amzmall.project.controller.dto.customer.CustomerRegisterDTO;
import com.amzmall.project.controller.dto.customer.CustomerUpdateDTO;
import com.amzmall.project.domain.customer.Customer;
import com.amzmall.project.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Tag(name = "customer", description = "회원 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/customer/")
public class MemberApiController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원가입")
    @GetMapping(value = "/customer/signup")
    public String signupForm() {
        return "/customer/signup";
    }

    // 회원 가입
    @Operation(summary = "회원가입")
    @PostMapping(value = "/customer/signup")
    public String signup(CustomerRegisterDTO customerRegisterDTO, RedirectAttributes redirectAttributes) {
        log.info(">>> 회원 가입 정보: {}", customerRegisterDTO);

        customerService.validateDuplicatedCustomer(customerRegisterDTO.getEmail());

        Customer generalCustomer = Customer.createCustomer(customerRegisterDTO, passwordEncoder);
        customerService.save(generalCustomer);

        redirectAttributes.addAttribute("customer", customerRegisterDTO.getEmail());
        return "redirect:/customer/signup-success";
    }

    @Operation(summary = "회원가입 성공")
    @GetMapping(value = "/customer/signup-success")
    public String signupSuccess(RedirectAttributes redirectAttributes) {
        log.info(">>> 회원 가입 성공, {}", redirectAttributes.getAttribute("customer"));
        return "/customer/signup-success";
    }
    @Operation(summary = "로그인")
    @GetMapping(value = "/customer/login")
    public String loginForm(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {
        if (error) {
            model.addAttribute("loginFailure", true);
            model.addAttribute("loginFailureMessage", "아이디와 패스워드를 확인하시고 다시 로그인해주세요.");
        }
        return "/customer/login";
    }
    @Operation(summary = "회원정보 호출")
    @GetMapping(value = "/customer/{customerId}")
    public CustomerDTO get(@PathVariable String customerId) {
        return null;
    }

    // TODO 회원 수정
    @Operation(summary = "회원정보 수정")
    @PostMapping(value = "/customer/update")
    public CustomerDTO update(@RequestBody CustomerUpdateDTO customerUpdateDTO) {
        return null;
    }
    @Operation(summary = "구매내역")
    @GetMapping(value = {"/customer/mypage", "/customer/mypage/"})
    public String myPage() {
        return "my-page";
    }

}
