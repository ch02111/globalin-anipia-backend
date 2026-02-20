package com.afivestudio.anipia.tag.presentation;

import com.afivestudio.anipia.tag.application.TagService;
import com.afivestudio.anipia.tag.application.dto.TagCreateDto;
import com.afivestudio.anipia.tag.application.dto.TagIdDto;
import com.afivestudio.anipia.tag.application.dto.TagUpdateDto;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RequestMapping("/tags")
@RestController
public class TagController {

    private final TagService tagService;

    @PostMapping
    public TagIdDto createTag(@RequestBody TagCreateDto dto) {
        long id = tagService.create(dto);
        return new TagIdDto(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateTag(
            @PathVariable @Positive long id,
            @RequestBody TagUpdateDto request
    ) {
        tagService.update(id, request.getText());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable @Positive long id) {
        tagService.delete(id);
    }
}
/*
public class TagController {

    private final TagService tagService;

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.findAll();
    }
}*/
