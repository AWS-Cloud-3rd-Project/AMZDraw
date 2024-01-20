package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.application.port.out.FindOrderPort;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderService implements ReadOrderUseCase {

    private final FindOrderPort findOrderPort;

    //주문 정보 단건 조회
    @Override
    public OrderDTO findOrderById(FindOrderCommand command) {
        OrderJpaEntity orderJpaEntity = findOrderPort.findOrderByOrderId(command.getOrderId());
        return toDto(orderJpaEntity);
    }

    // 주문 정보 목록 조회

    public OrderDTO toDto(OrderJpaEntity orderEntity) {
        return new OrderDTO(
                orderEntity.getOrderId(),
                orderEntity.getOrderStatus(),
                orderEntity.getOrderDate(),
                orderEntity.getOrderer(),
                orderEntity.getOrderAmount()
        );
    }
}
