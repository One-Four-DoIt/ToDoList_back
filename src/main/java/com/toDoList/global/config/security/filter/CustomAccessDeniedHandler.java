package com.toDoList.global.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toDoList.global.config.security.exception.SecurityException.AuthorityException;
import com.toDoList.global.config.security.jwt.JwtConstants.JwtExcpetionCode;
import com.toDoList.global.config.security.jwt.JwtConstants.JwtExcpetionMessage;
import com.toDoList.global.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("인증에 실패했습니다.");
        AuthorityException e = new AuthorityException(
                JwtExcpetionMessage.NON_AUTHORITY.getMessage(),
                JwtExcpetionCode.NON_AUTHORITY.getCode(),
                HttpStatus.FORBIDDEN);
        response.setStatus(e.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String errorResponseJson = objectMapper.writeValueAsString(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
        response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));
    }
}