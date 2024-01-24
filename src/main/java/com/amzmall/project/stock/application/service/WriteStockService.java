package com.amzmall.project.stock.application.service;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import com.amzmall.project.stock.application.port.in.StockCommand;
import com.amzmall.project.stock.application.port.in.WriteStockUseCase;
import com.amzmall.project.stock.application.port.out.FindStockPort;
import com.amzmall.project.stock.application.port.out.WriteStockPort;
import com.amzmall.project.stock.domain.Stock;
import com.amzmall.project.stock.domain.StockDTO;
import com.amzmall.project.stock.domain.events.StockDecreasedEvent;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteStockService implements WriteStockUseCase {

    private final WriteStockPort writeStockPort;
    private final FindStockPort findStockPort;
    private final ReadStockService readService;

    private final ApplicationEventPublisher eventPublisher;

    @Operation(summary = "재고 등록")
    @Override
    public StockDTO registerStock(StockCommand command) {
        Stock stock = Stock.builder()
                .stockId(command.getStockId())
                .quantity(command.getQuantity())
                .stockStatus(Stock.StockStatus.IN_STOCK) //재고 상태 초기화
                .build();

        StockJpaEntity stockEntity = writeStockPort.createStock(stock);
        return readService.toStockDTO(stockEntity);
    }

    //
    @Operation(summary = "재고 업데이트")
    @Override
    public StockDTO updateStock(StockCommand command) {

        // 재고 정보 조회
        StockJpaEntity stockEntity = findStockPort.findByStockId(command.getStockId());

        if (command.getQuantity() < 0) {
            throw new IllegalArgumentException("재고는 0보다 작을 수 없습니다.");
        }
        Stock updatedStock = Stock.builder()
                .stockId(stockEntity.getStockId())
                .quantity(command.getQuantity())
                .stockStatus(Stock.StockStatus.IN_STOCK) //재고 상태 업데이트
                .build();

        StockJpaEntity updatedStockEntity = writeStockPort.createStock(updatedStock);
        // 엔터티를 데이터베이스에 저장
        return readService.toStockDTO(updatedStockEntity);
    }

    @Operation(summary = "재고 감소")
    @Override
    public void decreaseStock(String stockId, int quantity) {

        // 재고 정보 조회
        StockJpaEntity stockEntity = findStockPort.findByStockId(stockId);

        Stock stock = Stock.builder()
                .stockId(stockEntity.getStockId())
                .quantity(stockEntity.getQuantity())
                .stockStatus(stockEntity.getStockStatus())
                .build();

        if (stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        stock.decreaseStock(quantity);
        if (stock.getQuantity() == 0) {
            stock.changeStockStatus(Stock.StockStatus.OUT_OF_STOCK);
        }

        eventPublisher.publishEvent(new StockDecreasedEvent(stock));
        writeStockPort.updateStock(stock);
    }
}
