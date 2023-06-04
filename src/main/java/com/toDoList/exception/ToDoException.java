package com.toDoList.exception;

import com.toDoList.dto.responseMessage.ToDoConstants;
import com.toDoList.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.toDoList.dto.responseMessage.ToDoConstants.FailCode.UNVALID_IDX_CODE;
import static com.toDoList.dto.responseMessage.ToDoConstants.FailMessage.UNVALID_IDX_MESSAGE;

public abstract class ToDoException extends ApplicationException {
    protected ToDoException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }

    public static class NoSuchToDoIdxException extends ToDoException {
        public NoSuchToDoIdxException() {
            super(UNVALID_IDX_MESSAGE.getMessage(), UNVALID_IDX_CODE.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}