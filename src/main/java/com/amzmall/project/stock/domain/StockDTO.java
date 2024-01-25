package com.amzmall.project.stock.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockDTO {

    //재고 번호
    private String stockId;
    //재고
    private Integer quantity;
    //생성 날짜
    private LocalDateTime createDate;
    //업데이트 날짜
    private LocalDateTime updateDate;
    //재고 상태
    private Stock.StockStatus stockStatus;

    public StockDTO(String stockId, Integer quantity, LocalDateTime createDate, LocalDateTime updateDate, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.stockStatus = stockStatus;
    }
}