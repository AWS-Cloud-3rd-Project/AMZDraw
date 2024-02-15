package com.amzmall.project.qna.domain.dto;

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
    private String admin;
    private String replyContent;
}
