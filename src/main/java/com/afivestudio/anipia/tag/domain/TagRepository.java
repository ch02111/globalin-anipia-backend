package com.afivestudio.anipia.tag.domain;

import java.util.Optional;

public interface TagRepository {

    void save(Tag tag);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByText(String text);

    void deleteById(Long id);
}
