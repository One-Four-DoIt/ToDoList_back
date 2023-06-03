package com.toDoList.dto.responseMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ToDoMessage {

    @Getter
    @RequiredArgsConstructor
    public enum SuccessMessage{

        SAVE_TODO_SUCCESS("ToDo 저장 성공했습니다.");
        private final String message;
    }
}
