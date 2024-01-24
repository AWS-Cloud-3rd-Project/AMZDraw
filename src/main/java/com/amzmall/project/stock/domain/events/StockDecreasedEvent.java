package com.amzmall.project.stock.domain.events;

import com.amzmall.project.stock.domain.Stock;
import lombok.Getter;


public class StockDecreasedEvent {

    @Getter
    private final Stock stock;

    public StockDecreasedEvent(Stock stock) {
        this.stock = stock;
    }
}