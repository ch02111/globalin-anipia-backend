package com.afivestudio.anipia.inquiry.query;

import com.afivestudio.anipia.inquiry.query.dto.InquirySearch;
import com.afivestudio.anipia.inquiry.query.dto.InquiryView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface InquiryQueryMapper {

    List<InquiryView> search(
            InquirySearch condition,
            Pageable pageable//offset + size 합쳐서 pageable
    );

    long count(@Param("condition") InquirySearch condition);

    InquiryView findById(@Param("id") Long id);
}

