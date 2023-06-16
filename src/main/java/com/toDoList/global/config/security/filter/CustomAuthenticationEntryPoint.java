package com.toDoList.global.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toDoList.global.config.security.exception.SecurityException.WrongTokenException;
import com.toDoList.global.config.security.jwt.JwtConstants.JwtExcpetionCode;
import com.toDoList.global.config.security.jwt.JwtConstants.JwtExcpetionMessage;
import com.toDoList.global.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        WrongTokenException e = new WrongTokenException(
                JwtExcpetionMessage.WRONG_TOKEN.getMessage(),
                JwtExcpetionCode.WRONG_TOKEN.getCode(),
                HttpStatus.FORBIDDEN);
        response.setStatus(e.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String errorResponseJson = objectMapper.writeValueAsString(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
        response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));
    }
}
