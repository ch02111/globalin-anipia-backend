package com.afivestudio.anipia.inquiry.domain;

import java.util.Optional;

public interface InquiryRepository {

    void save(Inquiry inquiry);

    void delete(long id);

    Optional<Inquiry> findById(long id);
}
