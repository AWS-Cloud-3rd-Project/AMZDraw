package com.amzmall.project.controller;

import ch.qos.logback.core.model.Model;
import com.amzmall.project.controller.cart.CartModelAttributeKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.amzmall.project.controller.dto.home.HomeDisplayDTO;
import com.amzmall.project.domain.customer.CustomerDetail;
import com.amzmall.project.service.HomeDisplayService;
import com.amzmall.project.service.cart.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeDisplayService homeDisplayService;
    private final CartService cartService;

    @GetMapping(value = {"/", "/index", ""})
    public String index(@AuthenticationPrincipal CustomerDetail customerDetail, Model model) {

        log.info(">>> Login Customer, {}", customerDetail);
        model.addAttribute("customerDetail", customerDetail);
        if (Objects.nonNull(customerDetail)) {
            int countCartProduct = cartService.countCartProduct(customerDetail.getCustomer().getCustomerId());
            model.addAttribute(CartModelAttributeKeys.CART_ITEM_COUNT_KEY, countCartProduct);
        }

        HomeDisplayDTO homeDisplayDTO = homeDisplayService.displayHome();


        return "/index";
    }
}
