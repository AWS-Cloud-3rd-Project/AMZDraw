package com.amzmall.project.users.domain.entity;

import com.amzmall.project.users.domain.dto.UserDto;
import com.amzmall.project.order.domain.entity.CancelOrder;
import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.qna.domain.entity.Question;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<Order> orders = new ArrayList<>();
    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUsers(this);
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<CancelOrder> cancelOrders = new ArrayList<>();
    public void addCancelPayment(CancelOrder cancelOrder) {
        this.cancelOrders.add(cancelOrder);
        cancelOrder.setUsers(this);
    }

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<Question> questions = new ArrayList<>();
    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setUsers(this);
    }

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Users(String email, String password) {
        this.email = email;
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .email(email)
                .createdAt(String.valueOf(createdAt))
                .build();
    }

}

