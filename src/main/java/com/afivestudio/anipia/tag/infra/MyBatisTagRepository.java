package com.afivestudio.anipia.tag.infra;

import com.afivestudio.anipia.tag.domain.Tag;
import com.afivestudio.anipia.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MyBatisTagRepository implements TagRepository {

    private final TagMapper mapper;

    @Override
    public void save(Tag tag) {
        if (tag.getId() == null) {
            mapper.insert(tag);
        } else {
            mapper.update(tag);
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Optional<Tag> findByText(String text) {
        return mapper.findByText(text);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }
}
