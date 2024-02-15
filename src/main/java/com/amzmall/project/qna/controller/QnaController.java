package com.amzmall.project.qna.controller;

import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.service.QnaService;
import com.amzmall.project.response.CommonResult;
import com.amzmall.project.response.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "QnA", description="문의 게시판")
@RequestMapping("/api/v1/qna")
public class QnaController {
    private final QnaService qnaService;
    private final ResponseService responseService;
    @PostMapping
    @Operation(summary = "Qna 요청", description = "문의하고 싶은 내역을 Qna 게시판에 등록합니다.")
    public CommonResult requestQna(
        @Parameter(name = "QnaReqDto", description = "요청 객체", required = true)
        @ModelAttribute QuestionReqDto qnaReqDto) {
        try {
            qnaService.registQuestion(qnaReqDto);
            return responseService.getSuccessResult();
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

}
