package com.amzmall.project.qna.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.dto.QuestionResDto;
import com.amzmall.project.qna.domain.dto.ReplyReqDto;
import com.amzmall.project.qna.domain.dto.ReplyResDto;
import com.amzmall.project.qna.domain.entity.Customer;
import com.amzmall.project.qna.domain.entity.Question;
import com.amzmall.project.qna.domain.entity.Reply;
import com.amzmall.project.qna.repository.CustomerRepository;
import com.amzmall.project.qna.repository.QuestionRepository;
import com.amzmall.project.qna.repository.ReplyRepository;
import com.amzmall.project.utility.RequestValidationUtil;
import java.util.List;
import java.util.Optional;
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
    
    // 문의 등록
    @Transactional
    public void postQuestion(QuestionReqDto questionReqDto) {
        RequestValidationUtil.validateQuestionRequestForm(questionReqDto);

        Customer customer = customerRepository.findByEmail(questionReqDto.getCustomerEmail())
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND));

        try {
            customer.addQuestion(questionReqDto.toEntity(customer.getEmail()));
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }

    }

    // 문의 비활성화
    @Transactional
    public void unAvailableQuestion(Long questionSq) {
        // 문의 조회
        Question question = questionRepository.findByQuestionSq(questionSq)
            .orElseThrow(() -> new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND));

        // 이미 비활성화된 문의인지 확인
        if (!question.isAvailable()) {
            throw new BusinessException(ExMessage.QUESTION_ERROR_NOT_AVAILABLE);
        }

        // 문의 비활성화
        question.deactivate();
    }
    
    // 문의 수정
    @Transactional
    public void updateQuestion(Long questionSq, String updatedContent) {
        // 문의 조회
        Question question = questionRepository.findByQuestionSq(questionSq)
            .orElseThrow(() -> new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND));

        // 이미 답변이 등록되어 있고, 답변이 available이 true인 경우 예외 처리
        if (question.getReply() != null && question.getReply().isAvailable()) {
            throw new BusinessException(ExMessage.REPLY_ERROR_ALREADY_REPLIED);
        }

        // 문의 수정
        question.update(updatedContent);
    }
    
    // 문의 전체 조회
    @Transactional(readOnly = true)
    public List<QuestionResDto> getAllQuestions(String customerEmail, PageRequest pageRequest) {
        Customer customer = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND));

        return customer.getQuestions()
            .stream().filter(Question::isAvailable)
            .map(Question::toQuestionDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void postReply(ReplyReqDto replyReqDto) {
        RequestValidationUtil.validateReplyRequestForm(replyReqDto);
        // 질문 조회
        Question question = questionRepository.findByQuestionSq(replyReqDto.getQuestionSq())
            .orElseThrow(() -> new BusinessException(ExMessage.QUESTION_ERROR_NOT_FOUND));

        // 질문의 available 확인
        if (!question.isAvailable()) {
            throw new BusinessException(ExMessage.QUESTION_ERROR_NOT_AVAILABLE);
        }

        // 이미 답변이 있는지 확인
        Optional<Reply> existingReplyOptional = replyRepository.findByQuestionQuestionSq(replyReqDto.getQuestionSq());
        if (existingReplyOptional.isPresent()) {
            Reply existingReply = existingReplyOptional.get();
            if (!existingReply.isAvailable()) {
                // 기존 답변이 존재하고 available이 false인 경우 기존 답변을 업데이트하여 새로운 답변으로 대체
                existingReply.update(replyReqDto);
            } else {
                // 기존 답변이 존재하고 available이 true인 경우 이미 답변이 등록되어 있음을 알림
                throw new BusinessException(ExMessage.REPLY_ERROR_ALREADY_REPLIED);
            }
        } else {
            // 기존 답변이 없을 경우 새로운 답변을 등록
            Reply newReply = Reply.createNew(replyReqDto, question);
            question.setReply(newReply);
            try {
                replyRepository.save(newReply);
            } catch (Exception e) {
                throw new BusinessException(ExMessage.DB_ERROR_SAVE);
            }
        }
    }

    @Transactional
    public void updateReply(Long replySq, String modifiedContent) {
        // 답변 조회
        Reply reply = replyRepository.findById(replySq)
            .orElseThrow(() -> new BusinessException(ExMessage.REPLY_ERROR_NOT_FOUND));

        // 기존 답변이 존재하고 available이 true인 경우에만 수정 가능
        if (reply.isAvailable()) {
            // 엔티티 객체에 수정 내용을 반영
            reply.updateContent(modifiedContent);
            // 수정된 엔티티 객체를 데이터베이스에 저장
            try {
                replyRepository.save(reply);
            } catch (Exception e) {
                throw new BusinessException(ExMessage.DB_ERROR_SAVE);
            }
        } else {
            // 답변이 존재하지 않거나 available 속성이 false로 설정되어 수정할 수 없는 경우
            throw new BusinessException(ExMessage.REPLY_ERROR_NOT_AVAILABLE_FOR_UPDATE);
        }
    }

    // 답변 비활성화
    @Transactional
    public void unAvailableReply(Long replySq) {
        // 답변 조회
        Reply reply = replyRepository.findByReplySq(replySq)
            .orElseThrow(() -> new BusinessException(ExMessage.REPLY_ERROR_NOT_FOUND));

        // 이미 비활성화된 답변인지 확인
        if (!reply.isAvailable()) {
            throw new BusinessException(ExMessage.REPLY_ERROR_ALREADY_DEACTIVATED);
        }

        // 답변 비활성화
        reply.deactivate();

        try {
            replyRepository.save(reply);
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
    }

    // 답변 조회
    @Transactional
    public ReplyResDto getReplyByQuestionSq(Long questionSq) {
        Reply reply = replyRepository.findByQuestionQuestionSq(questionSq)
            .orElseThrow(() -> new BusinessException(ExMessage.REPLY_ERROR_NOT_FOUND));

        return reply.toReplyDto();
    }
}
