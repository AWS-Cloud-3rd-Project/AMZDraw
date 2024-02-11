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
}
