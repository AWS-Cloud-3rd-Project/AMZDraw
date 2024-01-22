package com.amzmall.project.stock.domain;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Stock {

    public enum StockStatus {
        IN_STOCK,
        OUT_OF_STOCK
    }

    //TODO 상품명
    //TODO 상품번호
    //TODO 배송 번호?
    //재고 번호
    private final String stockId;
    //재고
    private final int quantity;
    //판매 가능 재고
    private final int availableQuantity;
    //생성 날짜
    private final LocalDateTime createDat;
    //업데이트 날짜
    private final LocalDateTime updateDat;
    //재고 상태
    private final StockStatus stockStatus;

    @Builder
    public Stock(String stockId, int quantity, int availableQuantity, LocalDateTime createDat, LocalDateTime updateDat, StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.createDat = createDat;
        this.updateDat = updateDat;
        this.stockStatus = stockStatus;
    }

}