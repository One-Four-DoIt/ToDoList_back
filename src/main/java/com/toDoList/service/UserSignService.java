package com.toDoList.service;

import com.toDoList.domain.User;
import com.toDoList.dto.UserDto.EditPassword;
import com.toDoList.dto.UserDto.SignUpRequest;
import com.toDoList.exception.UserAuthException;
import com.toDoList.exception.UserAuthException.NoSuchEmailException;
import com.toDoList.global.config.email.EmailService;
import com.toDoList.repository.springDataJpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserSignService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        String key = emailService.sendSimpleMessage(email);
        return key;
    }

    public boolean checkEmailDup(String email) {
        Optional<User> isDup = userRepository.findByEmail(email);
        if (isDup.isPresent())
            return false;
        return true;
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

    public void editPassword(EditPassword editPassword) {
        User user = userRepository.findByEmail(editPassword.getEmail()).orElseThrow(() -> new NoSuchEmailException());
        String password = bCryptPasswordEncoder.encode(editPassword.getPassword());
        user.editPassword(password);
    }
}
