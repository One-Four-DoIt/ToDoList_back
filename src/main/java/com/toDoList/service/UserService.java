package com.toDoList.service;

import com.toDoList.domain.User;
import com.toDoList.dto.UserDto;
import com.toDoList.dto.UserDto.LoginRequest;
import com.toDoList.dto.UserDto.SignUpRequest;
import com.toDoList.dto.UserDto.TokenResponse;
import com.toDoList.global.config.email.EmailService;
import com.toDoList.global.config.security.jwt.JwtTokenProvider;
import com.toDoList.global.config.security.jwt.TokenInfoResponse;
import com.toDoList.repository.springDataJpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public boolean checkEmailDup(String email) {
        Optional<User> isDup = userRepository.findByEmail(email);
        if (isDup.isPresent())
            return false;
        return true;
    }

    public String sendEmail(String email) throws Exception {
        String key = emailService.sendSimpleMessage(email);
        return key;
    }

    public User validateUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

    public boolean checkNickDup(String nickName) {
        Optional<User> isDup = userRepository.findByNickName(nickName);
        if (isDup.isPresent())
            return false;
        return true;
    }

    public void signUp(SignUpRequest signUpRequest) {
        String password = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        User user = signUpRequest.to(signUpRequest.getNickName(), signUpRequest.getEmail(), password);
        userRepository.save(user);
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
}
