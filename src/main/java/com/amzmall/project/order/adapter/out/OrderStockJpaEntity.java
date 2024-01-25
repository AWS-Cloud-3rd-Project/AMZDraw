package com.amzmall.project.order.adapter.out;

import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStockJpaEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderJpaEntity orderJpaEntity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockJpaEntity stockJpaEntity;

    public OrderStockJpaEntity(OrderJpaEntity orderJpaEntity, StockJpaEntity stockJpaEntity) {
        this.orderJpaEntity = orderJpaEntity;
        this.stockJpaEntity = stockJpaEntity;
    }
}
