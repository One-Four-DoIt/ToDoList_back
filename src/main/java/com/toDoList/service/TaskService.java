package com.toDoList.service;

import com.toDoList.domain.Task;
import com.toDoList.domain.ToDo;
import com.toDoList.dto.TaskDto;
import com.toDoList.dto.TaskDto.PostTaskDto;
import com.toDoList.dto.TaskDto.SelectTask;
import com.toDoList.dto.TaskDto.UpdateTaskDto;
import com.toDoList.repository.springDataJpa.TaskRepository;
import com.toDoList.repository.springDataJpa.ToDoRepository;
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
public class TaskService {
    private final TaskRepository taskRepository;
    private final ToDoRepository toDoRepository;

    public void saveTask(PostTaskDto postTaskDto) {
        ToDo toDo = toDoRepository.findById(postTaskDto.getTodoIdx()).orElseThrow();
        Task task = postTaskDto.to(toDo, postTaskDto.getTitle(), postTaskDto.getEndDate());
        taskRepository.save(task);
    }

    public void deleteTask(Long taskIdx){
        taskRepository.deleteById(taskIdx);
    }

    public void checkTask(Long taskIdx) {
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        log.info("todoIdx {}", task.getToDo().getToDoIdx());
        task.checkIsFin();
    }

    public void uncheckTask(Long taskIdx) {
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        task.unCheckIsFin();
    }

    public void updateTask(Long taskIdx, UpdateTaskDto updateTaskDto){
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        task.updateTask(updateTaskDto.getTitle(), updateTaskDto.getEndDate());
    }

    public List<SelectTask> orderBy(String orderBy, Long toDoIdx) {
        ToDo toDo = toDoRepository.findById(toDoIdx).orElseThrow();
        List<SelectTask> selectTasks = new ArrayList<>();
        selectTasks = taskRepository.findAllOrderBy(orderBy, toDo);
        return selectTasks;
    }
}
