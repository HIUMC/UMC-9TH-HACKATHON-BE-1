package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.controller;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req.CategoryReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.command.CategoryCommandService;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.ApiResponse;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorys")
@Validated
public class CategoryController {

    private final CategoryCommandService categoryCommandService;

    // TODO: 세션에서 ID 추출
    public ApiResponse<CategoryResDTO.CreateDTO> createCategory(
            @Valid CategoryReqDTO.CreateDTO dto,
            Long memberId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, categoryCommandService.createCategory(dto, memberId));
    }
}
