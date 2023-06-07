package com.toDoList.controller;

import com.toDoList.dto.UserDto.*;
import com.toDoList.global.dto.ResponseDto;
import com.toDoList.service.UserSignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.toDoList.dto.responseMessage.UserConstants.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Api(tags = "USER_SIGN API")
public class UserSignController {
    private final UserSignService userSignService;

    @PostMapping("/email")
    @ApiOperation(value = "이메일 인증 전송", notes = "http://~/user/email body에는 email")
    public ResponseEntity<ResponseDto<String>> sendEmail(@RequestBody Email email) {
        try {
            String key = userSignService.sendEmail(email.getEmail());
            return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_SEND_SUCCESS.getMessage(), key));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/email/duplicate")
    @ApiOperation(value = "이메일 중복체크", notes = "http://~/user/email/duplicate?email=값")
    public ResponseEntity<ResponseDto<Boolean>> checkEmail(String email) {
        boolean isDup = userSignService.checkEmailDup(email);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_DUP_SUCCESS.getMessage(), isDup));
    }

    @GetMapping("/nick/duplicate")
    @ApiOperation(value = "닉네임 중복체크", notes = "http://~/user/nick/duplicate?nickNamre=값")
    public ResponseEntity<ResponseDto<Boolean>> checkNick(String nickName) {
        boolean isDup = userSignService.checkNickDup(nickName);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), EMAIL_DUP_SUCCESS.getMessage(), isDup));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "http://~/user/signup body에 nickName, email, password")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequest signUpRequest) {
        userSignService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), SIGNUP_SUCCESS.getMessage()));
    }

    @PostMapping("/password-reset")
    @ApiOperation(value = "비밀번호 재설정", notes = "http://~/password-reset body에 email, password")
    public ResponseEntity<ResponseDto> login(@RequestBody EditPassword editPassword) {
        userSignService.editPassword(editPassword);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), EDIT_PASSWORD_SUCCESS.getMessage()));
    }
}
