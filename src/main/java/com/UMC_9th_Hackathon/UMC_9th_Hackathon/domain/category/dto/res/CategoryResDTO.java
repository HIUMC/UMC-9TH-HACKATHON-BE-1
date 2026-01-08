package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class CategoryResDTO {

    @Builder
    public record CreateDTO(
            Long id,
            String name,
            String imgUrl,
            LocalDateTime createdAt
    ){}

    @Builder
    public record CategoryDTO(
            Long id,
            String name,
            String imgUrl
    ){}
}
