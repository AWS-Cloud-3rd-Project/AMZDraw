package com.amzmall.project.stock.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStockRequest {

    //재고 번호
    private String stockId;
    //재고
    private int quantity;
    //판매 가능 재고
    private int availableQuantity;
}
