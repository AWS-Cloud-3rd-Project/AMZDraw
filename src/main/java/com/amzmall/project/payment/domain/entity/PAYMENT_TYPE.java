package com.amzmall.project.payment.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PAYMENT_TYPE {
	BRANDPAY("브랜드페이"),
	NORMAL("일반결제");
	private final String name;
}