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
@Api(tags = "TASK API 모든 API의 헤더에 Authorization 필요")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/post")
    @ApiOperation(value = "Task 저장", notes = "http://~~/task/post + body에 todoIdx, title, endDate (DateTime 객체를 만들고, 이를 \"yyyy-MM-dd'T'HH:mm:ss\" 형식의 문자열로 변환)")
    public ResponseEntity<ResponseDto> saveTask(@RequestBody PostTaskDto postTaskDto) {
        taskService.saveTask(postTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), POST_SUCCESS_MESSAGE.getMessage()));
    }

    @DeleteMapping("/{taskIdx}")
    @ApiOperation(value = "task 삭제", notes = "http://~~/task/1 (taskIdx 값)")
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable Long taskIdx){
        taskService.deleteTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/check/{taskIdx}")
    @ApiOperation(value = "task 완료 체크", notes = "http://~/task/check/1 (taskIdx 값)")
    public ResponseEntity<ResponseDto> checkTask(@PathVariable Long taskIdx){
        taskService.checkTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), CHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/un-check/{taskIdx}")
    @ApiOperation(value = "task 체크 해제", notes = "http://~/task/un-check/1 (taskIdx 값)")
    public ResponseEntity<ResponseDto> uncheckTask(@PathVariable Long taskIdx){
        taskService.uncheckTask(taskIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UNCHECK_SUCCESS_MESSAGE.getMessage()));
    }

    @PutMapping("/update/{taskIdx}")
    @ApiOperation(value = "task 수정", notes = "http://~/task/update/1 (taskIdx 값) body에는 title, endDate (위에서 설명한 형식으로 변환하여 요청")
    public ResponseEntity<ResponseDto> updateTask(@PathVariable Long taskIdx, @RequestBody UpdateTaskDto updateTaskDto){
        taskService.updateTask(taskIdx, updateTaskDto);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UPDATE_SUCCESS_MESSAGE.getMessage()));
    }

    @GetMapping("/order-by/{orderBy}/{toDoIdx}")
    @ApiOperation(value = "Task 정렬", notes = "http://~/task/order-by/newest/1 (taskIdx 값), http://~/task/order-by/oldest/1 (taskIdx 값)")
    public ResponseEntity<ResponseDto<List<SelectTask>>> toDoOrder(@PathVariable String orderBy, @PathVariable Long toDoIdx) {
        List<SelectTask> selectTasks = taskService.orderBy(orderBy, toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SELECT_SUCCESS_MESSAGE.getMessage(), selectTasks));
    }
}