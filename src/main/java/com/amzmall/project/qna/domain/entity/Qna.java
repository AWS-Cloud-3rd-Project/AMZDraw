package com.amzmall.project.qna.domain.entity;

import com.amzmall.project.qna.domain.dto.QnaResDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "qna")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Component
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnd_id")
    private Long qnaId;

    @Column(name = "qna_title")
    private String qnaTitle;

    @Column(name = "qna_content")
    private String qnaContent;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "qna_pwd")
    private String qnaPassword;

    @Column(name = "secret_qna_yn")
    @ColumnDefault("N")
    private String secretQnaYn;

    @Column(name = "comment")
    private String comment;

    @Column(name = "qna_answered_yn")
    private String qnaAnsweredYn;

    @Column(name = "admin")
    private String admin;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Customer customer;

    public QnaResDto toQnaResDto(){
        return QnaResDto.builder()
            .qnaId(qnaId)
            .qndTitle(qnaTitle)
            .qnaContent(qnaContent)
            .customerEmail(customerEmail)
            .qnaPassword(qnaPassword)
            .secretQnaYn(secretQnaYn)
            .comment(comment)
            .createdAt(createdAt)
            .qnaAnsweredYn(qnaAnsweredYn)
            .build();
    }
}