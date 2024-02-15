package com.amzmall.project.qna.domain.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaResDto {
    private Long qnaId;             // 문의 번호
    private String qndTitle;        // 문의 제목
    private String qnaContent;      // 문의 내용
    private String customerEmail;   // 문의자 이메일
    private String qnaPassword;     // 비밀 문의 비밀번호
    private String secretQnaYn;     // 비밀 문의 여부
    private String comment;         // 응답글
    private String qnaAnsweredYn;   // 문의 답변 여부
    private Timestamp createdAt;    // 생성 시기
}
