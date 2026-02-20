package com.afivestudio.anipia.tag.query;

import com.afivestudio.anipia.tag.query.dto.TagDto;
import com.afivestudio.anipia.tag.query.dto.TagSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface TagQueryMapper {

    List<TagDto> search(
            @Param("condition") TagSearchCondition condition,
            @Param("pageable") Pageable pageable);

    long count(@Param("condition") TagSearchCondition condition);
}