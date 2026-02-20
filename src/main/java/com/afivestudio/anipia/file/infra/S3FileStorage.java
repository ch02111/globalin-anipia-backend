package com.afivestudio.anipia.file.infra;

import com.afivestudio.anipia.config.properties.FileProperties;
import com.afivestudio.anipia.file.domain.FileStorage;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@RequiredArgsConstructor
@Component
public class S3FileStorage implements FileStorage {

    private final S3Presigner s3Presigner;
    private final FileProperties fileProperties;

    @Override
    public String generateUploadUrl(String filePath) {
        // 1. 파일 경로(이름)를 기반으로 Content-Type 추론
        String contentType = MediaTypeFactory.getMediaType(filePath)
                .orElse(MediaType.APPLICATION_OCTET_STREAM) // 못 찾으면 기본값(바이너리)
                .toString();

        // 2. 프리사인 URL 발급
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(fileProperties.s3().bucket())
                .key(filePath)
                .contentType(contentType) // ★ 동적으로 들어감
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(1L))
                .putObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }
}
