package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.converter;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req.CategoryReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;

public class CategoryConverter {

    public static Category toCategory(CategoryReqDTO.CreateDTO request, Member member) {
        return Category.builder()
                .name(request.name())
                .imgUrl(request.imgUrl())
                .member(member)
                .build();
    }

    public static CategoryResDTO.CreateDTO toCreateDTO(Category category) {
        return CategoryResDTO.CreateDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .imgUrl(category.getImgUrl())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
