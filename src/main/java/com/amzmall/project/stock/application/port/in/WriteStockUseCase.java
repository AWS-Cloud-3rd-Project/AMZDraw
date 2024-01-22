package com.amzmall.project.stock.application.port.in;

import com.amzmall.project.stock.domain.StockDTO;

public interface WriteStockUseCase {
    StockDTO registerStock(StockCommand command);

    StockDTO updateStock(StockCommand command);
}
