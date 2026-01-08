package com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Slf4j
@Configuration
public class AwsS3Config {
    @Value("${cloud.aws.credentials.access-key:}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider credentialsProvider;

        // accessKey가 설정되어 있으면 명시적 자격 증명 사용 (로컬 환경)
        // 설정되어 있지 않으면 DefaultCredentialsProvider 사용 (EC2 IAM Role 등)
        if (accessKey != null && !accessKey.isEmpty()) {
            log.info("Using static AWS credentials");
            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
            credentialsProvider = StaticCredentialsProvider.create(awsCredentials);
        } else {
            log.info("Using default AWS credentials provider (EC2 IAM Role, etc.)");
            credentialsProvider = DefaultCredentialsProvider.create();
        }

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }
}