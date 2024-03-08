package com.amzmall.project.qna.controller;

import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.QuestionResDto;
import com.amzmall.project.qna.domain.dto.ReplyReqDto;
import com.amzmall.project.qna.domain.dto.ReplyResDto;
import com.amzmall.project.qna.service.QnaService;
import com.amzmall.project.util.dto.CommonResult;
import com.amzmall.project.util.dto.ListResult;
import com.amzmall.project.util.service.ResponseService;
import com.amzmall.project.util.dto.SingleResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Operation(summary = "문의 등록", description = "문의를 등록합니다.")
    public CommonResult requestQuestion(
        @Parameter(name = "QuestionReqDto", description = "문의 요청 객체", required = true)
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

    @DeleteMapping
    @Operation(summary = "문의 삭제", description = "문의를 삭제합니다.")
    public CommonResult removeQuestion(
        @Parameter(name = "questionId", description = "문의 번호", required = true)
        @RequestParam("questionId") int questionId
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
    public CommonResult updateQuestion(
        @Parameter(description = "문의 번호", required = true)
        @RequestParam("questionId") int questionId,
        @Parameter(description = "수정 내용", required = true) 
        @RequestParam("questionContent") String questionContent
    ) {
        try {
            qnaService.updateQuestion(questionId, questionContent);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }
    
    @GetMapping("/all")
    @Operation(summary="모든 문의 내역 조회", description="고객의 활성화된 모든 문의 내역을 조회합니다.")
    public ListResult<QuestionResDto> getAllPayments(
        @Parameter(name = "customerEmail", description = "고객 이메일", required = true)
        @RequestParam("customerEmail") String customerEmail,
        @Parameter(name = "page", description = "페이지 번호 (0부터)", required = true)
        @RequestParam(name = "page",defaultValue = "0") int page,
        @Parameter(name = "size", description = "페이지 사이즈", required = true)
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        try {
            return responseService.getListResult(
                qnaService.getAllQuestions(customerEmail, pageRequest)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @PostMapping("/reply")
    @Operation(summary = "답변 등록", description = "문의에 대한 답변을 등록합니다.")
    public CommonResult writeReply(
        @Parameter(name = "ReplyReqDto", description = "답변 요청 객체", required = true)
        @RequestBody ReplyReqDto replyReqDto
    ) {
        try {
            qnaService.postReply(replyReqDto);
            return responseService.getSuccessResult();
        }  catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/reply")
    @Operation(summary = "답변 수정", description = "답변을 수정합니다.")
    public CommonResult updateReply(
        @Parameter(description = "답변 번호", required = true)
        @RequestParam("replyId") int replyId,
        @Parameter(description = "수정된 답변 내용", required = true)
        @RequestParam("modifiedReplyContent") String modifiedReplyContent
    ) {
        try {
            qnaService.updateReply(replyId, modifiedReplyContent);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }

    @DeleteMapping("/reply")
    @Operation(summary = "답변 삭제", description = "답변을 삭제합니다.")
    public CommonResult removeReply(
        @Parameter(name = " replyId", description = "답변 번호", required = true)
        @RequestParam(" replyId") int replyId
    ) {
        try {
            qnaService.unAvailableReply(replyId);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return responseService.getFailResult(
                -1,
                e.getMessage()
            );
        }
    }

    @GetMapping("/reply")
    @Operation(summary = "답변 조회", description = "문의 번호로 답변을 조회합니다.")
    public SingleResult<ReplyResDto> getReplyByQuestionId(
        @Parameter(name = "questionId", description = "문의 번호", required = true)
        @RequestParam("questionId") int questionId
    ) {
        try {
            return responseService.getSingleResult(qnaService.getReplyByQuestionId(questionId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
