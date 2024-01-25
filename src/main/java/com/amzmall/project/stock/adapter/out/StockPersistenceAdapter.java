package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.stock.application.port.out.FindStockPort;
import com.amzmall.project.stock.application.port.out.WriteStockPort;
import com.amzmall.project.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StockPersistenceAdapter implements WriteStockPort, FindStockPort {

    private final SpringDataStockRepository stockRepository;

    @Override
    public StockJpaEntity createStock(Stock stock) {
        return stockRepository.save(
                new StockJpaEntity(
                        stock.getStockId(),
                        stock.getQuantity(),
                        stock.getCreateDate(),
                        stock.getUpdateDate(),
                        stock.getStockStatus()
                )
        );
    }

    @Override
    public StockJpaEntity addStock(StockJpaEntity addedStockEntity) {
        return stockRepository.save(addedStockEntity);
    }

    @Override
    public StockJpaEntity findByStockId(String stockId) {
        return stockRepository.findByStockId(stockId);
    }

    @Override
    public List<StockJpaEntity> findAllByStockId(String stockId) {
        return null;
    }


//
//    @Override
//    public List<StockJpaEntity> findAllByOrderer(String orderer) {
//        return orderRepository.findAllByOrderer(orderer);
//    }
}
