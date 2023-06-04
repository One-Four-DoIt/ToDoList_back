package com.toDoList.controller;

import com.toDoList.dto.TaskDto.PostTaskDto;
import com.toDoList.dto.TaskDto.SelectTask;
import com.toDoList.dto.TaskDto.UpdateTaskDto;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toDoList.dto.responseMessage.TaskConstants.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/task")
@Api(tags = "TASK API")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> saveTask(@RequestBody PostTaskDto postTaskDto) {
        taskService.saveTask(postTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), POST_SUCCESS_MESSAGE.getMessage()));
    }

    @DeleteMapping("/{taskIdx}")
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable Long taskIdx){
        taskService.deleteTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/check/{taskIdx}")
    public ResponseEntity<ResponseDto> checkTask(@PathVariable Long taskIdx){
        taskService.checkTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), CHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/un-check/{taskIdx}")
    public ResponseEntity<ResponseDto> uncheckTask(@PathVariable Long taskIdx){
        taskService.uncheckTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UNCHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/update/{taskIdx}")
    public ResponseEntity<ResponseDto> updateTask(@PathVariable Long taskIdx, @RequestBody UpdateTaskDto updateTaskDto){
        taskService.updateTask(taskIdx, updateTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UPDATE_SUCCESS_MESSAGE.getMessage()));
    }

    @GetMapping("/order-by/{orderBy}/{toDoIdx}")
    @ApiOperation(value = "Task 정렬", notes = "Task 정렬 -> orderBy에 newest, oldest")
    public ResponseEntity<ResponseDto<List<SelectTask>>> toDoOrder(@PathVariable String orderBy, @PathVariable Long toDoIdx) {
        List<SelectTask> selectTasks = taskService.orderBy(orderBy, toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SELECT_SUCCESS_MESSAGE.getMessage(), selectTasks));
    }
}