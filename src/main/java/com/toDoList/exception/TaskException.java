package com.toDoList.exception;

import com.toDoList.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.toDoList.dto.responseMessage.TaskConstants.FailCode.UNVALID_IDX_CODE;
import static com.toDoList.dto.responseMessage.TaskConstants.FailMessage.UNVALID_IDX_MESSAGE;

public abstract class TaskException extends ApplicationException {
    protected TaskException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }

    public static class NoSuchTaskIdxException extends TaskException{

        public NoSuchTaskIdxException() {
            super(UNVALID_IDX_MESSAGE.getMessage(), UNVALID_IDX_CODE.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}