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
    //재고 번호
    private final String stockId;
    //재고
    private int quantity;
    //생성 날짜
    private final LocalDateTime createDate;
    //업데이트 날짜
    private final LocalDateTime updateDate;
    //재고 상태
    private StockStatus stockStatus;

    @Builder
    public Stock(String stockId, int quantity, LocalDateTime createDate, LocalDateTime updateDate, StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.stockStatus = stockStatus;
    }

    //재고 증가 로직
    public void increaseStock(int quantity) {
        this.quantity += quantity;
    }

    //재고 감소 로직
    public void decreaseStock(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }

    //재고 상태 변경 로직
    public void changeStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

}