package com.toDoList.repository.springDataJpa;

import com.toDoList.domain.User;
import com.toDoList.repository.queryDsl.UserQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQuerydslRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
}
