package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.controller;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.service.LoginService;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.ApiResponse;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    // 로그인 or 자동 회원가입

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResDTO.Login>> login(
            @RequestBody LoginReqDTO.Login request,
            HttpSession session
    ) {
        LoginResDTO.Login result = loginService.loginOrRegister(request, session);

        return ResponseEntity
                .status(GeneralSuccessCode.OK.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.OK, result));
    }
}
