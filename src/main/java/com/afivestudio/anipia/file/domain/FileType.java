package com.afivestudio.anipia.file.domain;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    IMAGE(Set.of("jpg", "jpeg", "png", "webp", "gif"), "image");

    private final Set<String> extensions;
    private final String mediaTypePrefix;

    public boolean isValidExtension(String extension) {
        return extensions.contains(extension.toLowerCase());
    }
}
