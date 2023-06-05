package com.toDoList.controller;

import com.toDoList.dto.ToDoDto.SaveToDoRequest;
import com.toDoList.dto.ToDoDto.UpdateToDoRequest;
import com.toDoList.dto.ToDoDto.SelectToDo;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.ToDoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toDoList.dto.responseMessage.ToDoConstants.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/todo")
@Api(tags = "TODO API")
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping("/post")
    @ApiOperation(value = "ToDo 저장", notes = "ToDo 저장 완료")
    public ResponseEntity<ResponseDto> saveToDo(@RequestBody SaveToDoRequest saveToDoRequest){
        toDoService.saveToDo(saveToDoRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), SAVE_TODO_SUCCESS.getMessage()));
    }

    @GetMapping(value = {"", "/order-by/{orderBy}"})
    @ApiOperation(value = "ToDo 정렬(/order-by/{orderBy}) 및 조회(그냥 /)", notes = "ToDo 정렬 -> orderBy에 newest, oldest, deadline 및 조회")
    public ResponseEntity<ResponseDto<List<SelectToDo>>> toDoOrder(@PathVariable @Nullable String orderBy) {
        List<SelectToDo> selectToDos = toDoService.orderBy(orderBy);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SELECT_SUCCESS.getMessage(), selectToDos));
    }

    @DeleteMapping("/{toDoIdx}")
    @ApiOperation(value = "ToDo 삭제", notes = "ToDo 삭제 완료")
    public ResponseEntity<ResponseDto> deleteToDo(@PathVariable Long toDoIdx){
        toDoService.deleteToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/check/{toDoIdx}")
    @ApiOperation(value = "ToDo 체크", notes = "ToDo 체크 완료")
    public ResponseEntity<ResponseDto> checkToDo(@PathVariable Long toDoIdx){
        toDoService.checkToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(),CHECK_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/unCheck/{toDoIdx}")
    @ApiOperation(value = "ToDo 체크 해제", notes = "ToDo 체크 해제 완료")
    public ResponseEntity<ResponseDto> unCheckToDo(@PathVariable Long toDoIdx){
        toDoService.unCheckToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UNCHECK_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/update/{toDoIdx}")
    @ApiOperation(value = "ToDo 수정",notes = "ToDo 수정 성공")
    public ResponseEntity<ResponseDto> updateToDo(@PathVariable Long toDoIdx, @RequestBody UpdateToDoRequest updateToDoRequest){
        toDoService.updateToDo(toDoIdx,updateToDoRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(),UPDATE_TODO_SUCCESS.getMessage()));
    }
}
