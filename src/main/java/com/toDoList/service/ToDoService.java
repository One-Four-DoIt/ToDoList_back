package com.toDoList.service;

import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;
import com.toDoList.dto.ToDoDto.SaveToDoRequest;
import com.toDoList.dto.ToDoDto.UpdateToDoRequest;
import com.toDoList.dto.ToDoDto.SelectToDo;
import com.toDoList.global.config.security.util.SecurityUtils;
import com.toDoList.repository.springDataJpa.ToDoRepository;
import com.toDoList.repository.springDataJpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<SelectToDo> orderBy(String orderBy) {
        User user = SecurityUtils.getLoggedInUser().orElseThrow();
        List<ToDo> toDos = toDoRepository.findAllOrderByStartDate(orderBy, user);
        List<SelectToDo> selectToDos = new ArrayList<>();
        for (int i = 0; i < toDos.size(); i++) {
            log.info("task = {}", toDos.get(i).getTasks());
            selectToDos.add(SelectToDo.from(toDos.get(i)));
        }
        return selectToDos;
    }

    public void deleteToDo(Long toDoIdx){
        toDoRepository.deleteById(toDoIdx);
    }

    public void checkToDo(Long toDoIdx){
        ToDo toDo = toDoRepository.findById(toDoIdx).orElseThrow();
        toDo.checkIsFin();
    }

    public void unCheckToDo(Long toDoIdx){
        ToDo toDo = toDoRepository.findById(toDoIdx).orElseThrow();
        toDo.uncheckIsFin();
    }

    public void updateToDo(Long toDoIdx, UpdateToDoRequest updateToDoRequest){
        ToDo toDo = toDoRepository.findById(toDoIdx).orElseThrow();
        toDo.updateToDo(updateToDoRequest.getTitle(),updateToDoRequest.getEndDate());
    }

}
