package com.toDoList.service;

import com.toDoList.domain.User;
import com.toDoList.repository.springDataJpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User validateUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }
}
