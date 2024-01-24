package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.stock.domain.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
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

    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //재고 상태
    private Stock.StockStatus stockStatus;


    public StockJpaEntity(String stockId, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stockStatus = stockStatus;
    }
}
