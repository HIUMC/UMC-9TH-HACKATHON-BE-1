package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.query;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;

import java.util.List;

public interface CategoryQueryService {
    List<CategoryResDTO.CategoryDTO> findAllCategories(Long memberId);
}
