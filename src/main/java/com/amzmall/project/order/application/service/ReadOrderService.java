package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.application.port.out.ReadOrderPort;
import com.amzmall.project.order.domain.OrderDTO;
import com.amzmall.project.order.domain.OrderStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadOrderService implements ReadOrderUseCase {

    private final ReadOrderPort readOrderPort;

    //주문 정보 단건 조회
    @Override
    public OrderDTO findByOrderId(FindOrderCommand command) {
        OrderJpaEntity orderJpaEntity = readOrderPort.findByOrderId(command.getOrderId());
        return toOrderDto(orderJpaEntity);
    }

    @Override
    public List<OrderDTO> findAllByOrderer(FindOrderCommand command) {
        var orderHistories = readOrderPort.findAllByOrderer(command.getOrderer());
        return orderHistories.stream()
                .map(this::toOrderDto)
                .toList();
    }

    // 주문 정보 목록 조회

    public OrderDTO toOrderDto(OrderJpaEntity orderEntity) {
        return new OrderDTO(
                orderEntity.getOrderId(),
                orderEntity.getOrderStatus(),
                orderEntity.getOrderDate(),
                orderEntity.getOrderer(),
                orderEntity.getOrderQuantity(),
                orderEntity.getOrderAmount()
        );
    }
    public OrderStatusDTO toOrderStatusDto(OrderJpaEntity orderEntity) {
        return new OrderStatusDTO(
                orderEntity.getOrderId(),
                orderEntity.getOrderStatus()
        );
    }

}
