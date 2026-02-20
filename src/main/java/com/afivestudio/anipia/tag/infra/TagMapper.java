package com.afivestudio.anipia.tag.infra;

import com.afivestudio.anipia.tag.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TagMapper {
    void insert(Tag tag);

    void update(Tag tag);

    void deleteById(Long id);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByText(String text);
}
