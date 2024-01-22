package com.amzmall.project.stock.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataStockRepository extends JpaRepository<StockJpaEntity, Long> {

    StockJpaEntity findByStockId(String stockId);
//
//    List<StockJpaEntity> findAllByOrderer(String orderer);
}
