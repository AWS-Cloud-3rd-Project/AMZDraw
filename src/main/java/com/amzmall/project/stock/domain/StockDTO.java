package com.amzmall.project.stock.domain;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockDTO {

    //재고 번호
    private String stockId;
    //재고
    private Integer quantity;
    //생성 날짜
    private LocalDateTime createDat;
    //업데이트 날짜
    private LocalDateTime updateDat;
    //재고 상태
    private Stock.StockStatus stockStatus;

    public StockDTO(String stockId, Integer quantity, LocalDateTime createDat, LocalDateTime updateDat, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createDat = createDat;
        this.updateDat = updateDat;
        this.stockStatus = stockStatus;
    }
}