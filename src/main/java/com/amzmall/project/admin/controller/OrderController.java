package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.order.OrderDetailView;
import com.amzmall.project.admin.exception.NotFoundOrderException;
import com.amzmall.project.admin.service.OrderService;
import com.amzmall.project.admin.service.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private static final String MENU_KEY = "orders";
    private static final String ORDERS_PAGE = "order_admin/orders/orders";
    private static final String ORDER_DETAIL_PAGE = "order_admin/orders/order-detail";

    private final OrderService orderService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<OrderDetailView> orderDetailViews = orderService.findAllOrderDetailView();
        addCommonModelAttributes(model);
        model.addAttribute("orders", orderDetailViews);
        return ORDERS_PAGE;
    }

    @GetMapping("/order-detail")
    public String detail(@RequestParam Long orderId, Model model) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new NotFoundOrderException("Not found order info"));
        addCommonModelAttributes(model);
        model.addAttribute("order", orderDTO);
        return ORDER_DETAIL_PAGE;
    }

    private void addCommonModelAttributes(Model model) {
        model.addAttribute("menuId", MENU_KEY);
        // Add common attributes here if needed
    }
}
