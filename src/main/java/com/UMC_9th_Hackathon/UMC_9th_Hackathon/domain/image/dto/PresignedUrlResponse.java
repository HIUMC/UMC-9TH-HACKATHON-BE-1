package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PresignedUrlResponse {

    private String presignedUrl;
    private String fileUrl;
    private Long expiresIn;
}
