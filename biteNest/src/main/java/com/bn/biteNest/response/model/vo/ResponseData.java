package com.bn.biteNest.response.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class ResponseData {

	private String message;
	private Object responseData;
}
