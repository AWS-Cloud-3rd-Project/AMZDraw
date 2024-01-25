package com.amzmall.project.stock.adapter.in;

import com.amzmall.project.stock.application.port.in.FindStockCommand;
import com.amzmall.project.stock.application.port.in.ReadStockUseCase;
import com.amzmall.project.stock.application.port.in.StockCommand;
import com.amzmall.project.stock.application.port.in.WriteStockUseCase;
import com.amzmall.project.stock.domain.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final WriteStockUseCase writeStockUseCase;
    private final ReadStockUseCase readStockUseCase;


    @PostMapping("/register")
    public StockDTO registerStock(@RequestBody RegisterStockRequest stockReq) {
        StockCommand command = StockCommand.builder()
                .stockId(stockReq.getStockId())
                .quantity(stockReq.getQuantity())
                .build();
        return writeStockUseCase.registerStock(command);
    }

    @PostMapping("/{stockId}/add")
    public StockDTO addStock(
            @PathVariable(name = "stockId") String stockId,
            @RequestBody AddStockRequest stockReq) {

        StockCommand command = StockCommand.builder()
                .stockId(stockId)
                .quantity(stockReq.getQuantity())
                .build();
        return writeStockUseCase.addStock(command);
    }

    //재고 정보 조회
    @GetMapping(path = "/{stockId}")
    public StockDTO findStock(@PathVariable(name = "stockId") String stockId) {

        FindStockCommand stockCmd = FindStockCommand.builder()
                .stockId(stockId)
                .build();

        return readStockUseCase.findByStockId(stockCmd);
    }

}
