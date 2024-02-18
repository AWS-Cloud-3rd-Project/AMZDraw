package com.amzmall.project.qna.domain.dto;

import com.amzmall.project.qna.domain.entity.Reply;
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
public class ReplyReqDto {
    @NotNull
    @Schema(description = "문의 번호")
    private Long questionSq;
    @NotNull
    @Schema(description = "관리자")
    private String adminEmail;
    @NotNull
    @Size(max = 1000, message = "답변 내용은 1000자를 초과할 수 없습니다.")
    @Schema(description = "답변 내용(1000자 까지)")
    private String content;
    public Reply toEntity() {
        return Reply.builder()
            .content(content)
            .adminEmail(adminEmail)
            .available(true)
            .build();
    }
}