package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.command;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req.CategoryReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;

public interface CategoryCommandService {
    CategoryResDTO.CreateDTO createCategory(CategoryReqDTO.CreateDTO request, Long memberId);
}
