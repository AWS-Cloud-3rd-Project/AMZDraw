package com.amzmall.project.qna.domain.entity;
import com.amzmall.project.customer.domain.entity.Customer;
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
    private int questionId;

    @Column(name = "question_title", nullable = false)
    private String questionTitle;

    @Column(name = "question_content", length = 1000)
    private String questionContent;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_replied", nullable = false)
    private boolean isReplied;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false) // 대문자로 변경
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reply reply;
    public void updateReply(Reply reply){
        this.reply = reply;
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    public Customer customer;

    public QuestionResDto toQuestionDto(){
        return QuestionResDto.builder()
            .questionId(questionId)
            .questionTitle(questionTitle)
            .questionContent(questionContent)
            .customerEmail(customerEmail)
            .isActive(isActive)
            .replyResDto(reply == null ? null : reply.toReplyDto())
            .isReplied(false)
            .createdAt(createdAt)
            .build();
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void update(String updatedQuestionContent) {
        this.questionContent = updatedQuestionContent;
    }

    public void updateIsReplied(boolean isReplied) {
        this.isReplied = isReplied;
    }

}