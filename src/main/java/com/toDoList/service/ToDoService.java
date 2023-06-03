package com.toDoList.service;

import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;
import com.toDoList.dto.ToDoDto.SaveToDoRequest;
import com.toDoList.dto.ToDoDto;
import com.toDoList.global.config.security.util.SecurityUtils;
import com.toDoList.repository.springDataJpa.ToDoRepository;
import com.toDoList.repository.springDataJpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public void saveToDo(SaveToDoRequest saveToDoRequest){
        User user = SecurityUtils.getLoggedInUser().orElseThrow();
        ToDo todo = saveToDoRequest.to(user,saveToDoRequest.getTitle(),saveToDoRequest.getEndDate());
        toDoRepository.save(todo);
    }
}
