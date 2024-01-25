package com.amzmall.project.order.application.port.out;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReadOrderPort {

    OrderJpaEntity findByOrderId(String orderId);

    List<OrderJpaEntity> findAllByOrderer(String orderer);
}
