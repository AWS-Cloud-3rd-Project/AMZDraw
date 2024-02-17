package com.amzmall.project.domain.cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.amzmall.project.domain.BaseEntity;

@Entity
@Table(name = "carts", schema = "ecommerce")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "customer_id")
    private Long customerId;
    public static Cart of(Long customerId) {
        Cart cart = new Cart();
        cart.customerId = customerId;
        return cart;
    }
}
