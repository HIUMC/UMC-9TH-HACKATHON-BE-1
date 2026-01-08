package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto;

public class LoginReqDTO {
    public record Login(
            String nickname,
            String password //숫자 6자리
    ) {}
}
