package com.toDoList.controller;

import com.toDoList.dto.UserDto.*;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.toDoList.dto.responseMessage.UserMessage.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Api(tags = "USER API")
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인을 진행합니다.")
    public ResponseEntity<ResponseDto<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse login = userAuthService.login(loginRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), login));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "accessToken 필요")
    public ResponseEntity<ResponseDto> logout(@RequestHeader("Authorization") String authorization) {
        userAuthService.logout(authorization);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGOUT_SUCCESS.getMessage()));
    }
}