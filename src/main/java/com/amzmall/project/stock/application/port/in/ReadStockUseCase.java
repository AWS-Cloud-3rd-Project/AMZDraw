package com.amzmall.project.stock.application.port.in;

import com.amzmall.project.stock.domain.StockDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReadStockUseCase {
    StockDTO findByStockId(FindStockCommand command);
//    List<StockDTO> findOrdersByOrderer(FindStockCommand command);

}
