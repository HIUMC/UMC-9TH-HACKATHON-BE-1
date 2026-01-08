package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.service;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginResDTO;
import jakarta.servlet.http.HttpSession;

public interface LoginService {
    LoginResDTO.Login loginOrRegister(LoginReqDTO.Login req, HttpSession session);
}
