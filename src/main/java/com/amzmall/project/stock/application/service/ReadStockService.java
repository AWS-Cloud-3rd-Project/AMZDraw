package com.amzmall.project.stock.application.service;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import com.amzmall.project.stock.application.port.in.FindStockCommand;
import com.amzmall.project.stock.application.port.in.ReadStockUseCase;
import com.amzmall.project.stock.application.port.out.FindStockPort;
import com.amzmall.project.stock.domain.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadStockService implements ReadStockUseCase {

    private final FindStockPort findStockPort;

    //재고 정보 단건 조회
    @Override
    public StockDTO findByStockId(FindStockCommand command) {
        StockJpaEntity stockJpaEntity = findStockPort.findByStockId(command.getStockId());
        return toStockDTO(stockJpaEntity);
    }

    //재고 목록 조회
//    @Override
//    public List<StockDTO> findOrdersByOrderer(FindStockCommand command) {
//        var orderHistories = findStockPort.findAllByOrderer(command.()); //판매자 아이디?
//        return orderHistories.stream()
//                .map(this::toStockDTO)
//                .toList();
//    }

    // 주문 정보 목록 조회

    public StockDTO toStockDTO(StockJpaEntity stockEntity) {
        return new StockDTO(
                stockEntity.getStockId(),
                stockEntity.getQuantity(),
                stockEntity.getCreateDate(),
                stockEntity.getUpdateDate(),
                stockEntity.getStockStatus()
        );
    }

}
