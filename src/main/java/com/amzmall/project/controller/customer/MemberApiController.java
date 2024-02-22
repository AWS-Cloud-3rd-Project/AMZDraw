package com.amzmall.project.controller.customer;

import com.amzmall.project.controller.dto.customer.CustomerDTO;
import com.amzmall.project.controller.dto.customer.CustomerUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "customer", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/")
public class MemberApiController {
    @GetMapping(value = "/customer/{customerId}")
    public CustomerDTO get(@PathVariable String customerId) {
        return null;
    }

    // TODO 회원 수정
    @PostMapping(value = "/customer/update")
    public CustomerDTO update(@RequestBody CustomerUpdateDTO customerUpdateDTO) {
        return null;
    }

}
