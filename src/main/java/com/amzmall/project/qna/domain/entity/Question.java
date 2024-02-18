package com.amzmall.project.qna.domain.entity;

import com.amzmall.project.qna.domain.dto.QuestionResDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "question")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Component
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_sq")
    private Long questionSq;

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 1000) // 대문자로 변경
    private String content;

    @Column(name = "customer_email") // 대문자로 변경
    private String customerEmail;

    @Column(name = "available") // 대문자로 변경
    private boolean available;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false) // 대문자로 변경
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at") // 대문자로 변경
    private Timestamp updatedAt;

    @Setter
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Reply reply;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Customer customer;

    public QuestionResDto toQuestionDto(){
        return QuestionResDto.builder()
            .questionSq(questionSq)
            .title(title)
            .content(content)
            .customerEmail(customerEmail)
            .available(available)
            .replyResDto(reply == null ? null : reply.toReplyDto())
            .createdAt(createdAt)
            .build();
    }

    public void deactivate() {
        this.available = false;
    }

    public void update(String updatedContent) {
        this.content = updatedContent;
    }
}