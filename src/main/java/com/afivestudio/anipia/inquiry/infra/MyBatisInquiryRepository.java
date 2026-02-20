package com.afivestudio.anipia.inquiry.infra;

import com.afivestudio.anipia.inquiry.domain.Inquiry;
import com.afivestudio.anipia.inquiry.domain.InquiryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MyBatisInquiryRepository implements InquiryRepository {

    private final InquiryMapper mapper;

    @Override
    public void save(Inquiry inquiry) {
        if (inquiry.getId() == null) {
            mapper.insert(inquiry);
        } else {
            mapper.update(inquiry);
        }
    }

    @Override
    public void delete(long id) {
        mapper.delete(id);
    }

    @Override
    public Optional<Inquiry> findById(long id) {
        return mapper.findById(id);
    }
}
