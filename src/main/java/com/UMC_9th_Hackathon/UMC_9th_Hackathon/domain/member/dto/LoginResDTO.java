package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto;

public class LoginResDTO {
    public record Login(
            Long memberId,
            String nickname,
            boolean isNewMember
    ) {}
}
