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
public class ReplyResDto {
    private Long replyId;
    private Long questionId;
    private String adminEmail;
    private String replyContent;
    private boolean available;
    private Timestamp createdAt;
}
