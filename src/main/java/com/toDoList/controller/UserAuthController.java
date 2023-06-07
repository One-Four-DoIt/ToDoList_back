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

import static com.toDoList.dto.responseMessage.UserConstants.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Api(tags = "USER_AUTH API")
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "http://~/user/login 그리고 body에 email, password")
    public ResponseEntity<ResponseDto<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse login = userAuthService.login(loginRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), login));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "http://~/user/logout 추가로 헤더에 Authorization라는 키에 발급받은 accessToekn 넣어야 함 -> 'Bearer ' + 'AccessToken' -> Bearer 뒤에 한칸 띄기 주의")
    public ResponseEntity<ResponseDto> logout(@RequestHeader("Authorization") String authorization) {
        userAuthService.logout(authorization);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGOUT_SUCCESS.getMessage()));
    }

    @PostMapping("/re-issue")
    @ApiOperation(value = "토큰 재발급", notes = "http://~/user/re-issue 이번에는 헤더의 Authroization에 refreshToken을 넣어야 합니다")
    public ResponseEntity<ResponseDto<TokenResponse>> reIssueToken(@RequestHeader("Authorization") String refreshToken) {
        TokenResponse tokenResponse = userAuthService.reIssueToken(refreshToken);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), REISSUE_SUCCESS.getMessage(), tokenResponse));
    }
}
