package com.amzmall.project.order.application.port.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindOrderCommand {
    private final String orderId;
    private final String orderer;

    public FindOrderCommand(String orderId, String orderer) {
        this.orderId = orderId;
        this.orderer = orderer;
    }
}
