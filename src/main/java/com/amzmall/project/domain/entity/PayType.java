package com.amzmall.project.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayType {
	CARD("카드"),
	TOSSPAY("토스페이"),
	VIRTUAL_ACCOUNT("가상계좌");
	private final String name;
}