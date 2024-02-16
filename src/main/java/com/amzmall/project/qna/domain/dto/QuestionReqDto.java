package com.amzmall.project.qna.domain.dto;

import com.amzmall.project.qna.domain.entity.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionReqDto {
    @NotNull
    @Schema(description = "문의 제목")
    private String questionTitle;
    @NotNull
    @Size(max = 1000, message = "문의 내용은 1000자를 초과할 수 없습니다.")
    @Schema(description = "문의 내용(1000자 까지)")
    private String questionContent;
    @NotNull
    @Schema(description = "고객 이메일")
    private String customerEmail;

    public Question toEntity(String customerEmail) {
        return Question.builder()
            .questionTitle(questionTitle)
            .questionContent(questionContent)
            .customerEmail(customerEmail)
            .isReplied(false)
            .available(true)
            .build();
    }
}