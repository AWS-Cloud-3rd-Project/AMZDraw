package com.amzmall.project.stock.adapter.in;

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

    @PostMapping("/register")
    public StockDTO registerStock(@RequestBody RegisterStockRequest stockReq) {
        StockCommand command = StockCommand.builder()
                .stockId(stockReq.getStockId())
                .quantity(stockReq.getQuantity())
                .availableQuantity(stockReq.getAvailableQuantity())
                .build();
        return writeStockUseCase.registerStock(command);
    }

    @PostMapping("/{stockId}/update")
    public StockDTO updateStock(
            @PathVariable(name = "stockId") String stockId,
            @RequestBody UpdateStockRequest stockReq) {

        StockCommand command = StockCommand.builder()
                .stockId(stockReq.getStockId())
                .quantity(stockReq.getQuantity())
                .availableQuantity(stockReq.getAvailableQuantity())
                .build();
        return writeStockUseCase.updateStock(stockId, command);
    }
}
