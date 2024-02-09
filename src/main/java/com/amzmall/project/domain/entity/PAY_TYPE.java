package com.amzmall.project.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PAY_TYPE {
	CARD("카드"),
	TOSSPAY("토스페이");
	private final String name;
}