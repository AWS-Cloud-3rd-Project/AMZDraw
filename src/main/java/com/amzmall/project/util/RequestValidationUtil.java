package com.amzmall.project.util;

import com.amzmall.project.util.advice.ExMessage;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.ReplyReqDto;

public class RequestValidationUtil {
    public static void validateQuestionRequestForm(QuestionReqDto questionReqDto) {
        // 요청 폼이 비어있는지 확인
        if (questionReqDto == null || questionReqDto.getQuestionTitle() == null || questionReqDto.getQuestionContent() == null || questionReqDto.getCustomerEmail() == null) {
            throw new BusinessException("문의글 요청" + ExMessage.ERROR_REQUEST_FORM);
        }
    }

    public static void validateReplyRequestForm(ReplyReqDto replyReqDto) {
        // 요청 폼이 비어있는지 확인
        if (replyReqDto == null || replyReqDto.getAdminEmail() == null || replyReqDto.getReplyContent() == null) {
            throw new BusinessException("답변 요청" + ExMessage.ERROR_REQUEST_FORM);
        }
    }
}
