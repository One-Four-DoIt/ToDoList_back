package com.toDoList.controller;

import com.toDoList.dto.TaskDto.PostTaskDto;
import com.toDoList.dto.TaskDto.UpdateTaskDto;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.TaskService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.toDoList.dto.responseMessage.TaskMessage.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/task")
@Api(tags = "TASK API")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> save(@RequestBody PostTaskDto postTaskDto) {
        taskService.save(postTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), POST_SUCCESS_MESSAGE.getMessage()));
    }

    @DeleteMapping("/{taskIdx}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long taskIdx){
        taskService.delete(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/check/{taskIdx}")
    public ResponseEntity<ResponseDto> check(@PathVariable Long taskIdx){
        taskService.check(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), CHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/un-check/{taskIdx}")
    public ResponseEntity<ResponseDto> uncheck(@PathVariable Long taskIdx){
        taskService.uncheck(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UNCHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/update/{taskIdx}")
    public ResponseEntity<ResponseDto> update(@PathVariable Long taskIdx, @RequestBody UpdateTaskDto updateTaskDto){
        taskService.update(taskIdx, updateTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UPDATE_SUCCESS_MESSAGE.getMessage()));
    }
}