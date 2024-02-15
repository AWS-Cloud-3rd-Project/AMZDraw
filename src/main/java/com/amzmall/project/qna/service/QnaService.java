package com.amzmall.project.qna.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.qna.domain.dto.QuestionReqDto;
import com.amzmall.project.qna.domain.entity.Customer;
import com.amzmall.project.qna.domain.entity.Qna;
import com.amzmall.project.qna.repository.CustomerRepository;
import com.amzmall.project.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void registQuestion(QuestionReqDto qnaReqDto) {
        boolean verify = verifyReq(qnaReqDto);
        if (!verify) throw new BusinessException("문의글 요청 형식이 잘못되었습니다.");

        Customer customer = customerRepository.findByEmail(qnaReqDto.getCustomerEmail())
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND));

        try {
            customer.addQna(qnaReqDto.toEntity(customer.getEmail()));
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }

    }

    private boolean verifyReq(QuestionReqDto qnaReqDto) {
        // 이메일이 존재하는지 확인
        if (customerRepository.findByEmail(qnaReqDto.getCustomerEmail()).isEmpty())
            return false;

        // 비밀글 여부가 "Y"인 경우 비밀번호가 반드시 존재해야 함
        if (qnaReqDto.getSecretQnaYn().equals("Y")){
            if(qnaReqDto.getQnaPassword() == null || qnaReqDto.getQnaPassword().isEmpty()) {
                throw new BusinessException("비밀번호를 입력해야 합니다.");
            }
            return false;
        }
        return true;
    }


}
