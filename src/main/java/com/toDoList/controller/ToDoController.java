package com.toDoList.controller;

import com.toDoList.dto.ToDoDto;
import com.toDoList.dto.ToDoDto.SaveToDoRequest;

import com.toDoList.dto.ToDoDto.SelectToDo;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.ToDoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toDoList.dto.responseMessage.ToDoMessage.SuccessMessage.*;

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

    @GetMapping("/order-by/{orderBy}")
    @ApiOperation(value = "ToDo 정렬", notes = "ToDo 정렬 -> orderBy에 newest, oldest, deadline")
    public ResponseEntity<ResponseDto<List<SelectToDo>>> toDoOrder(@PathVariable String orderBy) {
        List<SelectToDo> selectToDos = toDoService.orderBy(orderBy);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SELECT_ORDER_SUCCESS.getMessage(), selectToDos));
    }

}
