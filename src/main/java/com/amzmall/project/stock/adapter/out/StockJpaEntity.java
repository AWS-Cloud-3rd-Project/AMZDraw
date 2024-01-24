package com.amzmall.project.stock.adapter.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.stock.domain.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
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

    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //재고 상태
    private Stock.StockStatus stockStatus;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL) //배송과 재고 연결되어 있으므로 삭제 금지
    private List<DeliveryJpaEntity> deliveries;

    public StockJpaEntity(String stockId, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt, Stock.StockStatus stockStatus) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stockStatus = stockStatus;
    }
}
