package com.amzmall.project.stock.adapter.in;

import com.amzmall.project.stock.application.port.in.FindStockCommand;
import com.amzmall.project.stock.application.port.in.ReadStockUseCase;
import com.amzmall.project.stock.domain.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/stock")
public class FindStockController {

    private final ReadStockUseCase readStockUseCase;

    //재고 정보 조회
    @GetMapping(path = "/{stockId}")
    public StockDTO findStock(@PathVariable(name = "stockId") String stockId) {

        FindStockCommand stockCmd = FindStockCommand.builder()
                .stockId(stockId)
                .build();

        return readStockUseCase.findByStockId(stockCmd);
    }

    //재고 목록 조회 TODO: 판매자 아이디로 조회
//    @GetMapping(path = "/{orderer}/orderList")
//    public List<StockDTO> findOrdersByOrderer(@PathVariable(name = "orderer") String orderer) {
//
//        FindStockCommand ordersCommand = FindStockCommand.builder()
//                .orderer(orderer)
//                .build();
//
//        return readStockUseCase.findOrdersByOrderer(ordersCommand);
//    }
}
