package com.bn.biteNest.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseHandler {

    // 공통 응답 생성 메소드
    public ResponseEntity<Message> createResponse(String message, Object data, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(Message.builder().data(data).message(message).build());
    }

    // 공통 예외 처리 메소드
    public ResponseEntity<Message> handleException(String errorMessage, Exception e) {
        log.error("{}: {}", errorMessage, e.getMessage());
        return createResponse(errorMessage + ": " + e.getMessage(), "error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
