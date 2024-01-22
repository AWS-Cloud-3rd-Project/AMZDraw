package com.amzmall.project.stock.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@NonNull
public class StockCommand {

    //재고 번호
    private String stockId;
    //재고
    private int quantity;
    //판매 가능 재고
    private int availableQuantity;

    public StockCommand(String stockId, int quantity, int availableQuantity) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }
}
