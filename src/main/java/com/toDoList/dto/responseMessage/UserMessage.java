package com.toDoList.dto.responseMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserMessage {
    @Getter
    @RequiredArgsConstructor
    public enum SuccessMessage {
        SIGNUP_SUCCESS("회원가입에 성공했습니다."),
        LOGIN_SUCCESS("로그인에 성공했습니다."),
        LOGOUT_SUCCESS("로그아웃에 성공했습니다."),
        EDIT_PASSWORD_SUCCESS("패스워드 재설정에 성공했습니다."),
        EMAIL_SEND_SUCCESS("EMAIL 인증번호 전송에 성공했습니다."),
        EMAIL_DUP_SUCCESS("EMAIL 중복체크에 성공했습니다.");
        private final String message;
    }
}
