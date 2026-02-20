package com.afivestudio.anipia.inquiry.infra;

import com.afivestudio.anipia.inquiry.domain.Inquiry;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiryMapper {

    void insert(Inquiry inquiry);

    void update(Inquiry inquiry);

    void delete(long id);

    Optional<Inquiry> findById(long id);
}
