package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.repository;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
