package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PresignedUrlRequest {

    @NotBlank(message = "파일명은 필수입니다.")
    private String fileName;

    @NotBlank(message = "폴더명은 필수입니다.")
    private String folder;
}
