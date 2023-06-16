package com.toDoList.global.config.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toDoList.global.config.security.exception.SecurityException;
import com.toDoList.global.config.security.exception.SecurityException.*;
import com.toDoList.global.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            String errorResponseJson = getErrorResponseJson(request, response, e);
            response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));

        } catch (InvalidJwtFormatException e) {
            String errorResponseJson = getErrorResponseJson(request, response, e);
            response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));

        } catch (NonSupportedJwtException e) {
            String errorResponseJson = getErrorResponseJson(request, response, e);
            response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));

        } catch (WrongTokenException e) {
            String errorResponseJson = getErrorResponseJson(request, response, e);
            response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));

        } catch (UnKnownException e) {
            String errorResponseJson = getErrorResponseJson(request, response, e);
            response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));
        }
    }

    private String getErrorResponseJson(HttpServletRequest request, HttpServletResponse response, SecurityException e) throws JsonProcessingException {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String errorResponseJson = objectMapper.writeValueAsString(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
        return errorResponseJson;
    }
}
