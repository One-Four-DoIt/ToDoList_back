package com.toDoList.service;

import com.toDoList.domain.Task;
import com.toDoList.domain.ToDo;
import com.toDoList.dto.TaskDto.PostTaskDto;
import com.toDoList.dto.TaskDto.UpdateTaskDto;
import com.toDoList.repository.springDataJpa.TaskRepository;
import com.toDoList.repository.springDataJpa.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final ToDoRepository toDoRepository;

    public void save(PostTaskDto postTaskDto) {
        ToDo toDo = toDoRepository.findById(postTaskDto.getTodoIdx()).orElseThrow();
        Task task = postTaskDto.to(toDo, postTaskDto.getTitle(), postTaskDto.getEndDate());
        taskRepository.save(task);
    }

    public void delete(Long taskIdx){
        taskRepository.deleteById(taskIdx);
    }

    public void check(Long taskIdx) {
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        log.info("todoIdx {}", task.getToDo().getToDoIdx());
        task.checkIsFin();
    }

    public void uncheck(Long taskIdx) {
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        task.unCheckIsFin();
    }

    public void update(Long taskIdx, UpdateTaskDto updateTaskDto){
        Task task = taskRepository.findById(taskIdx).orElseThrow();
        task.editTask(updateTaskDto.getTitle(), updateTaskDto.getEndDate());
    }
}
