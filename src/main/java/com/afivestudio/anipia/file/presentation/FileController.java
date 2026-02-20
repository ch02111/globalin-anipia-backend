package com.afivestudio.anipia.file.presentation;

import com.afivestudio.anipia.file.application.FileService;
import com.afivestudio.anipia.file.application.dto.FileUploadRequest;
import com.afivestudio.anipia.file.application.dto.FileUploadUrlDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload-url")
    public FileUploadUrlDto generateUploadUrl(@RequestBody @Valid FileUploadRequest fileUploadRequest) {
        return fileService.generateUploadUrl(fileUploadRequest);
    }
}
