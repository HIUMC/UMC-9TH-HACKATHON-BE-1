package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.query;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.converter.CategoryConverter;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.repository.CategoryRepository;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception.MemberErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.exception.MemberException;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResDTO.CategoryDTO> findAllCategories(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.NOT_FOUND);
        }

        List<Category> categories = categoryRepository.findAllByMemberId(memberId);

        return CategoryConverter.toCategoryListDTO(categories);
    }
}
