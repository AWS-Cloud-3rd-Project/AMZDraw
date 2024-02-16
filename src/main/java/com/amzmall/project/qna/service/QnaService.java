package com.amzmall.project.qna.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.QuestionResDto;
import com.amzmall.project.qna.domain.entity.Customer;
import com.amzmall.project.qna.domain.entity.Question;
import com.amzmall.project.qna.domain.entity.Reply;
import com.amzmall.project.qna.repository.CustomerRepository;
import com.amzmall.project.qna.repository.QuestionRepository;
import com.amzmall.project.qna.repository.ReplyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void postQuestion(QuestionReqDto questionReqDto) {
        System.out.println(questionReqDto.getQuestionTitle());
        System.out.println(questionReqDto.getQuestionContent());
        System.out.println(questionReqDto.getCustomerEmail());
        Customer customer = customerRepository.findByEmail(questionReqDto.getCustomerEmail())
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND));

        try {
            customer.addQuestion(questionReqDto.toEntity(customer.getEmail()));
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }

    }
    @Transactional
    public void postReply(Long questionId, String adminEmail, String replyContent) {
        // 질문 조회
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND));
        if (question.isReplied()) { // 답변 존재 시 예외처리
            throw new BusinessException(ExMessage.REPLY_ERROR_ALREADY_REPLIED);
        } else {
            Reply reply = new Reply();
            reply.setAdminEmail(adminEmail);
            reply.setReplyContent(replyContent);
            question.setReplied(true);
            question.setReply(reply);
            // 답변 등록
            try {
                replyRepository.save(reply);
            } catch (Exception e) {
                throw new BusinessException(ExMessage.DB_ERROR_SAVE);
            }
        }
    }
    // 문의 비활성화
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
