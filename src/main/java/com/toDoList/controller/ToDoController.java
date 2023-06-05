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
@Api(tags = "TODO API 모든 API의 헤더에 Authorization 필요")
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping("/post")
    @ApiOperation(value = "ToDo 저장", notes = "http://~/todo/post  body에는 title, endDate 담아서 (DateTime 객체를 만들고, 이를 \"yyyy-MM-dd'T'HH:mm:ss\" 형식의 문자열로 변환)")
    public ResponseEntity<ResponseDto> saveToDo(@RequestBody SaveToDoRequest saveToDoRequest){
        toDoService.saveToDo(saveToDoRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), SAVE_TODO_SUCCESS.getMessage()));
    }

    @GetMapping(value = {"", "/order-by/{orderBy}"})
    @ApiOperation(value = "ToDo 정렬(/order-by/{orderBy}) 및 조회(그냥 /)", notes = "단순 조회 : http://~/todo  정렬 : http://~/todo/order-by/newest http://~/todo/order-by/oldest  http://~/todo/order-by/deadline ")
    public ResponseEntity<ResponseDto<List<SelectToDo>>> toDoOrder(@PathVariable @Nullable String orderBy) {
        List<SelectToDo> selectToDos = toDoService.orderBy(orderBy);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SELECT_SUCCESS.getMessage(), selectToDos));
    }

    @DeleteMapping("/{toDoIdx}")
    @ApiOperation(value = "ToDo 삭제", notes = "http://~/todo/1 (toDoIdx 값)")
    public ResponseEntity<ResponseDto> deleteToDo(@PathVariable Long toDoIdx){
        toDoService.deleteToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/check/{toDoIdx}")
    @ApiOperation(value = "ToDo 체크", notes = "http://~/todo/check/1 (toDoIdx 값)")
    public ResponseEntity<ResponseDto> checkToDo(@PathVariable Long toDoIdx){
        toDoService.checkToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(),CHECK_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/un-check/{toDoIdx}")
    @ApiOperation(value = "ToDo 체크 해제", notes = "http://~/todo/un-check/1 (toDoIdx 값)")
    public ResponseEntity<ResponseDto> unCheckToDo(@PathVariable Long toDoIdx){
        toDoService.unCheckToDo(toDoIdx);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UNCHECK_TODO_SUCCESS.getMessage()));
    }

    @PutMapping("/update/{toDoIdx}")
    @ApiOperation(value = "ToDo 수정",notes = "http://~/todo/update/1 (toDoIdx 값) body에는 title, endDate (형식은 위의 설명과 같이)")
    public ResponseEntity<ResponseDto> updateToDo(@PathVariable Long toDoIdx, @RequestBody UpdateToDoRequest updateToDoRequest){
        toDoService.updateToDo(toDoIdx,updateToDoRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(),UPDATE_TODO_SUCCESS.getMessage()));
    }
}
