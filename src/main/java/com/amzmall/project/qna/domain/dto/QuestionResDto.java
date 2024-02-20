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
public class QuestionResDto {
    private Long questionSq;            // 문의 번호
    private String title;               // 문의 제목
    private String content;             // 문의 내용
    private String customerEmail;       // 문의자 이메일
    private Boolean available;          // 문의글 사용 여부
    private Boolean isReplied;          // 문의 답변 여부
    private ReplyResDto replyResDto;
    private Timestamp createdAt;        // 생성 시기
}
