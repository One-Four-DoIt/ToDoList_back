package com.toDoList.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private LocalDateTime localDateTime;
    private String errorCode;
    private String message;

    public static ErrorResponseDto create(String errorCode, String message) {
        return ErrorResponseDto.builder()
                .localDateTime(LocalDateTime.now().withNano(0))
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
