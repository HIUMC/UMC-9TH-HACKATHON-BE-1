package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.service;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.converter.MemberConverter;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.dto.LoginResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception.LoginFailedException;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SESSION_MEMBER_ID = "MEMBER_ID";
    private static final String SESSION_NICKNAME  = "NICKNAME";

    @Override
    public LoginResDTO.Login loginOrRegister(LoginReqDTO.Login req, HttpSession session) {
        String nickname = normalize(req.nickname());
        String pin = req.password(); // 비밀번호 6자리

       // 6자리 아니면 실패
        validate(nickname, pin);

        return memberRepository.findByNickname(nickname)
                .map(member -> loginExisting(member, pin, session))   // 있으면 로그인
                .orElseGet(() -> registerAndLogin(nickname, pin, session)); // 없으면 자동가입+로그인
    }

    /** 기존 회원 로그인 */
    private LoginResDTO.Login loginExisting(Member member, String pin, HttpSession session) {
        // 비번 불일치 => 실패(401)
        if (!passwordEncoder.matches(pin, member.getPasswordHash())) {
            throw new LoginFailedException();
        }

        attachSession(session, member);
        return MemberConverter.toLoginRes(member, false);
    }

    private LoginResDTO.Login registerAndLogin(String nickname, String pin, HttpSession session) {
        Member newMember = Member.builder()
                .nickname(nickname)
                .passwordHash(passwordEncoder.encode(pin)) // 저장은 해시
                .build();

        try {
            Member saved = memberRepository.save(newMember);
            attachSession(session, saved);
            return MemberConverter.toLoginRes(saved, true);
        } catch (DataIntegrityViolationException e) {
            // 동시성: 다른 요청이 먼저 같은 nickname을 생성했을 수 있음 -> 다시 조회 후 로그인 처리
            Member member = memberRepository.findByNickname(nickname)
                    .orElseThrow(LoginFailedException::new);

            return loginExisting(member, pin, session);
        }
    }

    private void validate(String nickname, String pin) {
        if (nickname == null || nickname.isBlank()) {
            throw new LoginFailedException();
        }
        // 숫자 6자리 고정
        if (pin == null || !pin.matches("\\d{6}")) {
            throw new LoginFailedException();
        }
    }

    //세션에 로그인 정보 저장
    private void attachSession(HttpSession session, Member member) {
        session.setAttribute(SESSION_MEMBER_ID, member.getId());
        session.setAttribute(SESSION_NICKNAME, member.getNickname());
    }

    private String normalize(String s) {
        return s == null ? null : s.trim();
    }
}
