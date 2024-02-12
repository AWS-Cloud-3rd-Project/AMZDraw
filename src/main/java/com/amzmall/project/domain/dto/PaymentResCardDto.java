package com.amzmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResCardDto {
    String company;						// 카드사, "국민"
    String number;						// 카드번호, 41102653****600*
    String useCardPoint;				// 카드로 결제할 때 설정하는 카드사 포인트 사용 여부 (false)
    String cardType;					// 카드 종류 (신용, 체크, 기프트)
    String ownerType;					// 카드 소유자 타입(개인, 법인)

    String receiptUrl;                  // 결제수단별 영수증
    String canceledAt;                  // 결제 취소가 일어난 날짜와 시간, yyyy-MM-dd'T'HH:mm:ss±hh:mm
    String cancelReason;                // 결제를 취소하는 이유

}
