package com.amzmall.project.stock.application.port.out;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReadStockPort {

    StockJpaEntity findByStockId(String stockId);
    List<StockJpaEntity> findAllByStockId(String stockId);
//
//    List<StockJpaEntity> findAllByOrderer(String orderer);
}
