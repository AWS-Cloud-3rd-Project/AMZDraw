package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.stock.domain.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //재고 번호
    private String stockId;
    //재고
    private int quantity;
    //판매 가능 재고
    private int availableQuantity;
    //생성 날짜
    private LocalDateTime createDat;
    //업데이트 날짜
    private LocalDateTime updateDat;
    //재고 상태
    private Stock.StockStatus stockStatus;// TODO enum으로 변경 (재고 있음, 재고 없음)

    public StockJpaEntity(String stockId, int quantity, int availableQuantity, LocalDateTime createDat, LocalDateTime updateDat, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.createDat = createDat;
        this.updateDat = updateDat;
        this.stockStatus = stockStatus;
    }
}
