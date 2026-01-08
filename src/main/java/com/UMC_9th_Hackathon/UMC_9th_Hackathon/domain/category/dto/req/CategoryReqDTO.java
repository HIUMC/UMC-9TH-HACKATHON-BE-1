package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryReqDTO {

    public record CreateDTO(
            @NotBlank(message = "카테고리 이름은 필수입니다.")
            @Size(max = 12, message = "카테고리 이름은 최대 12자까지 가능합니다.")
            String name,
            @NotBlank(message = "카테고리 이미지는 필수입니다.")
            String imgUrl
    ) {}
}
