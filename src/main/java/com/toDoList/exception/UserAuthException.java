package com.toDoList.exception;

import com.toDoList.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.toDoList.dto.responseMessage.UserConstant.FailCode.UNVALID_EMAIL_CODE;
import static com.toDoList.dto.responseMessage.UserConstant.FailMessage.UNVALID_EMAIL_MESSAGE;

public abstract class UserAuthException extends ApplicationException {

    protected UserAuthException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }

    public static class NoSuchEmailException extends UserAuthException {
        public NoSuchEmailException() {
            super(UNVALID_EMAIL_MESSAGE.getMessage(), UNVALID_EMAIL_CODE.getCode(), HttpStatus.BAD_REQUEST);
        }
    }
}
