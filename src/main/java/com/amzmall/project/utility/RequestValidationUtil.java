package com.amzmall.project.utility;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.ReplyReqDto;

public class RequestValidationUtil {
    public static void validateQuestionRequestForm(QuestionReqDto questionReqDto) {
        // 요청 폼이 비어있는지 확인
        if (questionReqDto == null || questionReqDto.getTitle() == null || questionReqDto.getContent() == null || questionReqDto.getCustomerEmail() == null) {
            throw new BusinessException(ExMessage.QUESTION_ERROR_REQUEST_FORM);
        }
    }

    public static void validateReplyRequestForm(ReplyReqDto replyReqDto) {
        // 요청 폼이 비어있는지 확인
        if (replyReqDto == null || replyReqDto.getQuestionId() == null || replyReqDto.getAdminEmail() == null || replyReqDto.getContent() == null) {
            throw new BusinessException(ExMessage.REPLY_ERROR_REQUEST_FORM);
        }
    }
}
