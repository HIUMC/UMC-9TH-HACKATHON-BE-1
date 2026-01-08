package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.command;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.converter.CategoryConverter;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req.CategoryReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.exception.CategoryException;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.exception.code.CategoryErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.repository.CategoryRepository;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService{

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public CategoryResDTO.CreateDTO createCategory(CategoryReqDTO.CreateDTO request, Long memberId) {

        // TODO: Member 에러 코드로 바꾸기
        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));

        if(categoryRepository.existsByMemberAndName(member, request.name())) {
            throw new CategoryException(CategoryErrorCode.ALREADY_EXISTS);
        }

        Category category = categoryRepository.save(CategoryConverter.toCategory(request, member));

        return CategoryConverter.toCreateDTO(category);
    }
}
