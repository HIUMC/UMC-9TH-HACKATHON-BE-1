package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.controller;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req.CategoryReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res.CategoryResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.service.command.CategoryCommandService;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.ApiResponse;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

    private final CategoryCommandService categoryCommandService;

    @PostMapping
    public ApiResponse<CategoryResDTO.CreateDTO> createCategory(
            @Valid @RequestBody CategoryReqDTO.CreateDTO dto,
            @SessionAttribute(name = "MEMBER_ID") Long memberId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, categoryCommandService.createCategory(dto, memberId));
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<Void> deleteCategory(
            @PathVariable Long categoryId,
            @SessionAttribute(name = "MEMBER_ID") Long memberId
    ) {
        categoryCommandService.deleteCategory(categoryId, memberId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
