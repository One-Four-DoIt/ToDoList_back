package com.toDoList.global.exception;

import com.toDoList.exception.TaskException.NoSuchTaskIdxException;
import com.toDoList.exception.ToDoException.NoSuchToDoIdxException;
import com.toDoList.exception.UserAuthException.NoSuchEmailException;
import com.toDoList.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(NoSuchToDoIdxException.class)
    public ResponseEntity<ErrorResponseDto> handleNoSuchToDoIdxException(NoSuchToDoIdxException e) {
        log.error(LOG_FORMAT, e.getClass(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(NoSuchTaskIdxException.class)
    public ResponseEntity<ErrorResponseDto> handleNoSuchTaskIdxException(NoSuchTaskIdxException e) {
        log.error(LOG_FORMAT, e.getClass(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(NoSuchEmailException.class)
    public ResponseEntity<ErrorResponseDto> handleNoSuchEmailException(NoSuchEmailException e) {
        log.error(LOG_FORMAT, e.getClass(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(ApplicationException e) {
        log.error(LOG_FORMAT, e.getClass(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(ErrorResponseDto.create(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error(LOG_FORMAT, e.getClass(), 500, "Server Exception");
        return ResponseEntity.ok(ErrorResponseDto.create(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Server Exception"));
    }
}
