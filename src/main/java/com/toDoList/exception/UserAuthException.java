package com.toDoList.exception;

import com.toDoList.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.toDoList.dto.responseMessage.UserConstants.FailCode.UNVALID_EMAIL_CODE;
import static com.toDoList.dto.responseMessage.UserConstants.FailCode.UNVALID_TOKEN_CODE;
import static com.toDoList.dto.responseMessage.UserConstants.FailMessage.UNVALID_EMAIL_MESSAGE;
import static com.toDoList.dto.responseMessage.UserConstants.FailMessage.UNVALID_TOKEN_MESSAGE;

public abstract class UserAuthException extends ApplicationException {

    protected UserAuthException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }

    public static class NoSuchEmailException extends UserAuthException {
        public NoSuchEmailException() {
            super(UNVALID_EMAIL_MESSAGE.getMessage(), UNVALID_EMAIL_CODE.getCode(), HttpStatus.BAD_REQUEST);
        }
    }

    public static class NoSuchTokenException extends UserAuthException {

        public NoSuchTokenException() {
            super(UNVALID_TOKEN_MESSAGE.getMessage(), UNVALID_TOKEN_CODE.getCode(), HttpStatus.BAD_REQUEST);
        }
    }
}
