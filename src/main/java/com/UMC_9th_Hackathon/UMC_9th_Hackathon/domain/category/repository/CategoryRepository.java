package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.repository;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByMemberAndName(Member member, String name);

    Optional<Category> findByIdAndMember(Long id, Member member);

    List<Category> findAllByMemberId(Long memberId);
}
