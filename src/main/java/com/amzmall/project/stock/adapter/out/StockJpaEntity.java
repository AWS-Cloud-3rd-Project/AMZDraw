package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.order.adapter.out.OrderStockJpaEntity;
import com.amzmall.project.stock.domain.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    //재고 상태
    private Stock.StockStatus stockStatus;

    @OneToMany(mappedBy = "stockJpaEntity")
    private List<OrderStockJpaEntity> orderStockJpaEntities = new ArrayList<>();

    public StockJpaEntity(String stockId, int quantity, LocalDateTime createDate, LocalDateTime updateDate, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.stockStatus = stockStatus;
    }
}
