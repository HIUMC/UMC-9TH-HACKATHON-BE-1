package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.controller;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto.PresignedUrlRequest;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto.PresignedUrlResponse;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.service.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Image", description = "이미지 업로드 및 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class AmazonS3Controller {

    private final AwsS3Service awsS3Service;

    @Operation(
            summary = "S3 업로드용 Presigned URL 생성",
            description = "클라이언트가 S3에 직접 업로드할 수 있는 Presigned URL을 발급합니다. " +
                    "업로드 시 PUT 메서드로 요청해야 하며, 요청 헤더에 응답으로 받은된 Content-Type을 포함해야 합니다."
    )
    @PostMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(
            @Valid @RequestBody PresignedUrlRequest request) {
        return ResponseEntity.ok(awsS3Service.getPresignedUrl(request));
    }

    @Operation(summary = "S3 파일 삭제", description = "S3에 저장된 파일을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.ok("파일이 삭제되었습니다: " + fileName);
    }
}
