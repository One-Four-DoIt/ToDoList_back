package com.toDoList.controller;

import com.toDoList.dto.UserDto.*;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.UserService;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/email")
    @ApiOperation(value = "이메일 인증 전송", notes = "이메일 인증을 전송합니다.")
    public ResponseEntity<ResponseDto<String>> sendEmail(@RequestBody Email email) {
        try {
            String key = userService.sendEmail(email.getEmail());
            return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_SEND_SUCCESS.getMessage(), key));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/email/duplicate")
    @ApiOperation(value = "이메일 중복체크", notes = "이메일 중복체크를 합니다.")
    public ResponseEntity<ResponseDto<Boolean>> checkEmail(String email) {
        boolean isDup = userService.checkEmailDup(email);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_DUP_SUCCESS.getMessage(), isDup));
    }

    @GetMapping("/nick/duplicate")
    @ApiOperation(value = "닉네임 중복체크", notes = "닉네임 중복체크를 합니다.")
    public ResponseEntity<ResponseDto<Boolean>> checkNick(String nickName) {
        boolean isDup = userService.checkNickDup(nickName);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_DUP_SUCCESS.getMessage(), isDup));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "회원가입 완료합니다.")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), SIGNUP_SUCCESS.getMessage()));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인을 진행합니다.")
    public ResponseEntity<ResponseDto<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse login = userService.login(loginRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), login));
    }

    @PostMapping("/password-reset")
    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호를 재설정 합니다.")
    public ResponseEntity<ResponseDto> login(@RequestBody EditPassword editPassword) {
        userService.editPassword(editPassword);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), EDIT_PASSWORD_SUCCESS.getMessage()));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "accessToken 필요")
    public ResponseEntity<ResponseDto> logout(@RequestHeader("Authorization") String authorization) {
        userService.logout(authorization);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGOUT_SUCCESS.getMessage()));
    }
}
