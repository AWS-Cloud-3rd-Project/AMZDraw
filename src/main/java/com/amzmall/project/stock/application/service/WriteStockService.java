package com.amzmall.project.stock.application.service;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import com.amzmall.project.stock.application.port.in.StockCommand;
import com.amzmall.project.stock.application.port.in.WriteStockUseCase;
import com.amzmall.project.stock.application.port.out.FindStockPort;
import com.amzmall.project.stock.application.port.out.RegisterStockPort;
import com.amzmall.project.stock.domain.Stock;
import com.amzmall.project.stock.domain.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteStockService implements WriteStockUseCase {

    private final RegisterStockPort registerStockPort;
    private final FindStockPort findStockPort;
    private final ReadStockService readService;

    //현재 시간
    LocalDateTime currentTime = LocalDateTime.now();
    @Operation(summary = "재고 등록")
    @Override
    public StockDTO registerStock(StockCommand command) {

        //재고 상태 초기화
        Stock stock = Stock.builder()
                .stockId(command.getStockId())
                .quantity(command.getQuantity())
                .availableQuantity(command.getAvailableQuantity())
                .createDat(currentTime) //현재 시간
                .updateDat(currentTime) //현재 시간
                .stockStatus(Stock.StockStatus.IN_STOCK) //재고 상태 초기화
                .build();

        StockJpaEntity stockEntity = registerStockPort.save(stock);
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
        // 재고 정보 업데이트
        Stock stock = Stock.builder()
                .stockId(stockEntity.getStockId())
                .quantity(command.getQuantity())
                .availableQuantity(command.getAvailableQuantity())
                .createDat(stockEntity.getCreateDat())
                .updateDat(currentTime) //현재 시간
                .stockStatus(Stock.StockStatus.IN_STOCK) //재고 상태 업데이트
                .build();

        // 엔터티를 데이터베이스에 저장
        StockJpaEntity updatedStockEntity = registerStockPort.save(stock);
        return readService.toStockDTO(updatedStockEntity);
    }
}
