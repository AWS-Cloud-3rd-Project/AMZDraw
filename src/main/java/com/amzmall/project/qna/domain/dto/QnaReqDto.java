package com.amzmall.project.qna.domain.dto;

import com.amzmall.project.qna.domain.entity.Qna;
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
public class QnaReqDto {
    @NotNull
    @Schema(description = "문의 제목")
    private String qnaTitle;
    @NotNull
    @Size(max = 1000, message = "문의 내용은 1000바이트를 초과할 수 없습니다.")
    @Schema(description = "문의 내용(1000바이트 까지)")
    private String qnaContent;
    @NotNull
    @Schema(description = "고객 이메일")
    private String customerEmail;
    @Schema(description = "문의글 비밀번호")
    private String qnaPassword;
    @NotNull
    @Schema(description = "비밀글 여부")
    private String secretQnaYn;

    public Qna toEntity() {
        return Qna.builder()
            .qnaTitle(qnaTitle)
            .qnaContent(qnaContent)
            .customerEmail(customerEmail)
            .secretQnaYn(secretQnaYn)
            .qnaPassword(qnaPassword)
            .build();
    }

}