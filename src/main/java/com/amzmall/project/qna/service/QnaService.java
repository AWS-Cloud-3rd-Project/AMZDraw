package com.amzmall.project.qna.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.entity.Customer;
import com.amzmall.project.qna.domain.entity.Question;
import com.amzmall.project.qna.domain.entity.Reply;
import com.amzmall.project.qna.repository.CustomerRepository;
import com.amzmall.project.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void postQuestion(QuestionReqDto questionReqDto) {
        boolean verify = verifyReq(questionReqDto);
        if (!verify) throw new BusinessException(ExMessage.QUESTION_ERROR_REQUEST_FORM);

        Customer customer = customerRepository.findByEmail(questionReqDto.getCustomerEmail())
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND));

        try {
            customer.addQna(questionReqDto.toEntity(customer.getEmail()));
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }

    }

    private boolean verifyReq(QuestionReqDto qnaReqDto) {
        // 이메일이 존재하는지 확인
        if (customerRepository.findByEmail(qnaReqDto.getCustomerEmail()).isEmpty())
            return false;

        return true;
    }

    @Transactional
    public void postReply(Long questionId, String adminEmail, String replyContent) {
        // 질문 조회
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND));
        if (question.isReplied()) {
            throw new BusinessException(ExMessage.REPLY_ERROR_ALREADY_REPLIED);
        }
        Reply reply = new Reply();
        reply.setAdmin(adminEmail);
        reply.setReplyContent(replyContent);
        question.setReply(reply);
        // 답변 등록
        try {
            questionRepository.save(question);    // 변경된 질문 엔티티 저장
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
    }

    @Transactional
    public void unAvailableQuestion(Long questionId) {
        questionRepository.findByQuestionId(questionId)
            .ifPresentOrElse(
                R -> R.setAvailable(false)
                , () -> {
                    throw new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND);
                }
            );
    }

}
