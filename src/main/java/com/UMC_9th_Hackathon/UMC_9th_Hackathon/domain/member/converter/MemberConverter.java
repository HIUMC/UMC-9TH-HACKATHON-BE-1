package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.converter;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginResDTO;

public class MemberConverter {
    public static LoginResDTO.Login toLoginRes(Member member, boolean isNew) {
        return new LoginResDTO.Login(member.getId(), member.getNickname(), isNew);
    }
}
