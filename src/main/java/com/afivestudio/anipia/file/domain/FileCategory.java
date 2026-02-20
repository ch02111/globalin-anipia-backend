package com.afivestudio.anipia.file.domain;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileCategory {
    // 1. 도메인별 디렉토리 구조 정의
    // users/{id}/...
    USER_PROFILE("users", "images", FileType.IMAGE),

    // animations/{id}/...
    ANIMATION_BANNER("animations", "images", FileType.IMAGE);

    private final String rootPath;
    private final String subPath;
    private final FileType fileType;

    // 2. 경로 생성 로직 (확장성 포인트)
    public String buildPath(Long ownerId, String originalFileName) {
        String extension = getExtension(originalFileName);

        // [핵심] 검증 로직을 FileType에게 위임
        if (!this.fileType.isValidExtension(extension)) {
            throw new IllegalArgumentException(
                    String.format("지원하지 않는 파일 형식입니다. (%s 만 허용)", this.fileType.getExtensions())
            );
        }

        // 구조: {디렉토리}/{ID}/{UUID}.{확장자}
        // 예: animations/55/a1b2-c3d4.png
        String uuid = UUID.randomUUID().toString();
        return String.format("%s/%d/%s/%s.%s", this.rootPath, ownerId, this.subPath, uuid, extension);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
