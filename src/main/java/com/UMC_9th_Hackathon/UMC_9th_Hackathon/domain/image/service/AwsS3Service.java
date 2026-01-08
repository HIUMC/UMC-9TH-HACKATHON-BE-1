package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.service;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto.PresignedUrlRequest;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.image.dto.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final Long PRESIGNED_URL_EXPIRATION = 10L; // 10분

    /**
     * Presigned URL 생성 (업로드용)
     */
    public PresignedUrlResponse getPresignedUrl(PresignedUrlRequest request) {
        String key = request.getFolder() + "/" + request.getFileName();

        try (S3Presigner presigner = S3Presigner.create()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(PRESIGNED_URL_EXPIRATION))
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);

            String presignedUrl = presignedRequest.url().toString();
            String fileUrl = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucket, key);

            log.info("Generated presigned URL for key: {}", key);

            return PresignedUrlResponse.builder()
                    .presignedUrl(presignedUrl)
                    .fileUrl(fileUrl)
                    .expiresIn(PRESIGNED_URL_EXPIRATION * 60) // 초 단위
                    .build();
        }
    }

    /**
     * S3 파일 삭제
     */
    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("Deleted file from S3: {}", fileName);
        } catch (Exception e) {
            log.error("Error deleting file from S3: {}", fileName, e);
            throw new RuntimeException("파일 삭제 중 오류가 발생했습니다.", e);
        }
    }
}
