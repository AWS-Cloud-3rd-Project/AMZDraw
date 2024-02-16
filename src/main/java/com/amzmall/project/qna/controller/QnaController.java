package com.amzmall.project.qna.controller;

import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.QuestionResDto;
import com.amzmall.project.qna.service.QnaService;
import com.amzmall.project.response.CommonResult;
import com.amzmall.project.response.ListResult;
import com.amzmall.project.response.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Operation(summary = "1대1 문의 요청", description = "문의하고 싶은 내역을 Qna 게시판에 등록합니다.")
    public CommonResult requestQna(
        @Parameter(name = "QuestionReqDto", description = "요청 객체", required = true)
        @RequestBody QuestionReqDto questionReqDto)
//      @ModelAttribute QuestionReqDto questionReqDto)
        {
        try {
            qnaService.postQuestion(questionReqDto);
            return responseService.getSuccessResult();
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @PostMapping("/reply")
    @Operation(summary = "문의 답글", description = "문의에 대한 대답을 작성합니다.")
    public CommonResult writeReply(
        @Parameter(name = "questionId", description = "문의 번호", required = true)
        @RequestParam("questionId") Long questionId,
        @Parameter(name = "adminEmail", description = "관리자 이메일", required = true)
        @RequestParam("adminEmail") String adminEmail,
        @Parameter(name = "replyContent", description = "답글 내용", required = true)
        @RequestParam("replyContent") String replyContent
    ) {
        try {
            qnaService.postReply(questionId, adminEmail, replyContent);
            return responseService.getSuccessResult();
        }  catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping
    @Operation(summary = "문의 삭제", description = "문의를 삭제합니다.")
    public CommonResult removeReview(
        @Parameter(name = "questionId", description = "문의 번호", required = true)
        @RequestParam("questionId") Long questionId
    ) {
        try {
            qnaService.unAvailableQuestion(questionId);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }

    @PutMapping
    @Operation(summary = "문의 수정", description = "문의를 수정합니다.")
    public CommonResult updateReview(
        @Parameter(description = "문의 번호", required = true)
        @RequestParam("questionId") Long questionId,
        @Parameter(description = "수정 내용", required = true) 
        @RequestParam("questionContent") String questionContent
    ) {
        try {
            qnaService.updateReview(questionId, questionContent);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }

}
