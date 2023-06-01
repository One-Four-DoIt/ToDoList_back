package com.toDoList.dto;

import com.toDoList.domain.User;
import com.toDoList.global.config.security.jwt.TokenInfoResponse;
import lombok.*;

public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequest {
        private String nickName;
        private String email;
        private String password;

        public User to(String nickName, String email, String password) {
            return User.builder()
                    .email(email)
                    .nickName(nickName)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Email {
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenResponse {
        private String accessToken;
        private String refreshToken;

        public static TokenResponse from(TokenInfoResponse tokenInfoResponse) {
            return TokenResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .build();
        }
    }
}
