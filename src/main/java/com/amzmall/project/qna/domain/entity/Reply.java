package com.amzmall.project.qna.domain.entity;

import com.amzmall.project.qna.domain.dto.ReplyReqDto;
import com.amzmall.project.qna.domain.dto.ReplyResDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "reply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_sq")
    private Long replySq;

    @Column(name = "reply_content", length = 2000, nullable = false)
    private String replyContent;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_sq")
    private Question question;

    public ReplyResDto toReplyDto() {
        return ReplyResDto.builder()
            .replySq(replySq)
            .replyContent(replyContent)
            .adminEmail(adminEmail)
            .questionId(question.getQuestionSq())
            .isActive(isActive)
            .createdAt(createdAt)
            .build();
    }

    public void update(ReplyReqDto replyReqDto) {
        this.adminEmail = replyReqDto.getAdminEmail();
        this.replyContent = replyReqDto.getReplyContent();
        this.isActive = true; // 새로운 답변으로 업데이트
    }

    public static Reply createNewReply(ReplyReqDto replyReqDto, Question question) {
        Reply reply = new Reply();
        reply.question = question;
        reply.adminEmail = replyReqDto.getAdminEmail();
        reply.replyContent = replyReqDto.getReplyContent();
        reply.isActive = true;
        return reply;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void updateContent(String modifiedContent) {
        this.replyContent = modifiedContent;
    }
}


