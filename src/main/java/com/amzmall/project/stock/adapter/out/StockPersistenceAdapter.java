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

    private final SpringDataStockRepository stockRepository;
    @Override
    public StockJpaEntity save(Stock stock) {
        return stockRepository.save(
                new StockJpaEntity(
                        stock.getStockId(),
                        stock.getQuantity(),
                        stock.getCreateDat(),
                        stock.getUpdateDat(),
                        stock.getStockStatus()
                )
        );
    }

    @Override
    public void decreaseStock(String stockId, int quantity) {
        StockJpaEntity stockJpaEntity = stockRepository.findByStockId(stockId);
        stockJpaEntity.setQuantity(stockJpaEntity.getQuantity() - quantity);
        stockRepository.save(stockJpaEntity);
    }


    @Override
    public StockJpaEntity findByStockId(String stockId) {
        return stockRepository.findByStockId(stockId);
    }
//
//    @Override
//    public List<StockJpaEntity> findAllByOrderer(String orderer) {
//        return orderRepository.findAllByOrderer(orderer);
//    }
}
