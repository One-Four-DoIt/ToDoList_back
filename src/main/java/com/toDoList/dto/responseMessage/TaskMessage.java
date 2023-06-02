package com.toDoList.dto.responseMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class TaskMessage {
    @Getter
    @RequiredArgsConstructor
    public enum SuccessMessage {
        POST_SUCCESS_MESSAGE("Task 작성에 성공했습니다."),
        DELETE_SUCCESS_MESSAGE("Task 삭제에 성공했습니다."),
        UPDATE_SUCCESS_MESSAGE("Task 수정에 성공했습니다."),
        UNCHECK_SUCCESS_MESSAGE("Task 체크 해제에 성공했습니다."),
        CHECK_SUCCESS_MESSAGE("Task 체크에 성공했습니다.");
        private final String message;
    }
}
