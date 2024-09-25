package com.bn.biteNest.common;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Message {
	private String message;
	private Object data;
}
