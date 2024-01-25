package com.amzmall.project.stock.application.port.out;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import com.amzmall.project.stock.domain.Stock;
import org.springframework.stereotype.Component;

public interface WriteStockPort {

    StockJpaEntity createStock(Stock stock);

    StockJpaEntity addStock(StockJpaEntity addedStockEntity);

}
