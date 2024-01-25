package com.amzmall.project.stock.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.adapter.out.OrderStockJpaEntity;
import com.amzmall.project.order.application.port.out.ReadOrderPort;
import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import com.amzmall.project.stock.application.port.in.StockCommand;
import com.amzmall.project.stock.application.port.in.WriteStockUseCase;
import com.amzmall.project.stock.application.port.out.ReadStockPort;
import com.amzmall.project.stock.application.port.out.WriteStockPort;
import com.amzmall.project.stock.domain.Stock;
import com.amzmall.project.stock.domain.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WriteStockService implements WriteStockUseCase {

    private final WriteStockPort writeStockPort;
    private final ReadStockPort readStockPort;
    private final ReadStockService readService;

    LocalDateTime currentDateTime = LocalDateTime.now();

    @Operation(summary = "재고 등록")
    @Override
    public StockDTO registerStock(StockCommand command) {
        Stock stock = Stock.builder()
                .stockId(command.getStockId())
                .quantity(command.getQuantity())
                .stockStatus(Stock.StockStatus.IN_STOCK) //재고 상태 초기화
                .createDate(currentDateTime)
                .updateDate(currentDateTime)
                .build();

        StockJpaEntity stockEntity = writeStockPort.createStock(stock);
        //로그
        log.info("registeredStock : {}", stockEntity);
        return readService.toStockDTO(stockEntity);
    }

    @Operation(summary = "재고 추가")
    @Override
    public StockDTO addStock(StockCommand command) {
            // 재고 정보 조회
            StockJpaEntity stockEntity = readStockPort.findByStockId(command.getStockId());

            if (stockEntity == null) {
                throw new IllegalArgumentException("재고가 존재하지 않습니다.");
                }
            stockEntity.setQuantity(stockEntity.getQuantity() + command.getQuantity());
            stockEntity.setUpdateDate(currentDateTime); // 현재 시간으로 업데이트 날짜 설정
            log.info("stockQuantity : {}", stockEntity.getQuantity());

            StockJpaEntity addedStockEntity = writeStockPort.addStock(stockEntity);
            log.info("addedStock : {}", addedStockEntity);

            return readService.toStockDTO(addedStockEntity);

    }

}

