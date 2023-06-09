package com.toDoList.dto.responseMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ToDoConstants {

    @Getter
    @RequiredArgsConstructor
    public enum SuccessMessage{
        SAVE_TODO_SUCCESS("ToDo 저장 성공했습니다."),
        SELECT_SUCCESS("ToDo 우선순위 조회에 성공했습니다."),
        DELETE_TODO_SUCCESS("ToDo 삭제 성공했습니다."),
        CHECK_TODO_SUCCESS("ToDo 체크 성공했습니다."),
        UNCHECK_TODO_SUCCESS("ToDo 체크 해제 성공했습니다."),
        UPDATE_TODO_SUCCESS("ToDo 수정 성공했습니다. ");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum FailMessage {
        UNVALID_IDX_MESSAGE("해당 IDX가 존재하지 않습니다.");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum FailCode {
        UNVALID_IDX_CODE("TD001");
        private final String code;
    }
}
