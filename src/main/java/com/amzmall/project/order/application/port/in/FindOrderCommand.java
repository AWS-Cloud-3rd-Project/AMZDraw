package com.amzmall.project.order.application.port.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindOrderCommand {
    private final String orderId;

    public FindOrderCommand(String orderId) {
        this.orderId = orderId;
    }
}
