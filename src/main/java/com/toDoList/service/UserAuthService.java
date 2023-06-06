package com.toDoList.service;

import com.toDoList.domain.User;
import com.toDoList.dto.UserDto.LoginRequest;
import com.toDoList.dto.UserDto.TokenResponse;
import com.toDoList.exception.UserAuthException;
import com.toDoList.exception.UserAuthException.NoSuchEmailException;
import com.toDoList.exception.UserAuthException.NoSuchTokenException;
import com.toDoList.global.config.redis.RedisRepository;
import com.toDoList.global.config.security.jwt.JwtTokenProvider;
import com.toDoList.global.config.security.jwt.TokenInfoResponse;
import com.toDoList.global.config.security.util.SecurityUtils;
import com.toDoList.repository.springDataJpa.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserAuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisRepository redisRepository;

    public User validateUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchEmailException());
        return user;
    }

    public TokenResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("securityContextHolder 저장");
        TokenInfoResponse tokenInfoResponse = jwtTokenProvider.createToken(authentication);
        return TokenResponse.from(tokenInfoResponse);
    }

    public void logout(String authorization) {
        String token = authorization.substring(7);
        String userIdx = jwtTokenProvider.getUserIdx(token);
        try {
            Long expiration = jwtTokenProvider.getExpiration(token);
            redisRepository.setValues("blackList:" + token, token, Duration.ofSeconds(expiration)); //Access Token 남은 시간동안 블랙리스트
        } catch (ExpiredJwtException e) {
        } finally {
            redisRepository.deleteValues(String.valueOf(userIdx));
        }
    }

    public TokenResponse reIssueToken(String refreshToken) {
        Long userIdx = SecurityUtils.getLoggedInUser().orElseThrow().getUserIdx();
        String token = redisRepository.getValues(userIdx.toString()).orElseThrow(() -> new NoSuchTokenException());

        if (!token.equals(refreshToken.substring(7)))
            throw new NoSuchTokenException();

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        TokenInfoResponse tokenInfoResponse = jwtTokenProvider.createToken(authentication);
        log.info("재발급 & 저장");
        return TokenResponse.from(tokenInfoResponse);
    }
}
