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
    @Column(name = "question_id")
    private Long questionId;

    @Setter
    @Column(name = "question_title")
    private String questionTitle;

    @Setter
    @Column(name = "question_content", length = 1000)
    private String questionContent;

    @Setter
    @Column(name = "customer_email")
    private String customerEmail;

    @Setter
    @Column(name = "available")
    private boolean available;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne
    @Builder.Default
    @JoinColumn(name="reply_id")
    private Reply reply = null;
    public void setReply(Reply reply) {
        this.reply = reply;
        reply.setQuestion(this);
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Customer customer;

    public QuestionResDto toQuestionDto(){
        return QuestionResDto.builder()
            .questionId(questionId)
            .questionTitle(questionTitle)
            .questionContent(questionContent)
            .customerEmail(customerEmail)
            .available(available)
            .replyResDto(reply == null ? null : reply.toReplyDto())
            .createdAt(createdAt)
            .build();
    }
}