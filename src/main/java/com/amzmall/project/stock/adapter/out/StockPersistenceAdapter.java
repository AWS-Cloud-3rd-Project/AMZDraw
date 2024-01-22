package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.stock.application.port.out.FindStockPort;
import com.amzmall.project.stock.application.port.out.RegisterStockPort;
import com.amzmall.project.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StockPersistenceAdapter implements RegisterStockPort, FindStockPort {

    private final SpringDataStockRepository orderRepository;
    @Override
    public StockJpaEntity save(Stock stock) {
        return orderRepository.save(
                new StockJpaEntity(
                        stock.getStockId(),
                        stock.getQuantity(),
                        stock.getAvailableQuantity(),
                        stock.getCreateDat(),
                        stock.getUpdateDat(),
                        stock.getStockStatus()
                )
        );
    }

    @Override
    public StockJpaEntity findByStockId(String stockId) {
        return orderRepository.findByStockId(stockId);
    }
//
//    @Override
//    public List<StockJpaEntity> findAllByOrderer(String orderer) {
//        return orderRepository.findAllByOrderer(orderer);
//    }
}
