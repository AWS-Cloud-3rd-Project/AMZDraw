package com.amzmall.project.stock.application.port.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindStockCommand {
    private String stockId;

    public FindStockCommand(String stockId) {
        this.stockId = stockId;
    }


}
