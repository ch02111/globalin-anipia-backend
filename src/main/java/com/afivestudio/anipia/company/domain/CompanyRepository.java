package com.afivestudio.anipia.company.domain;

import java.util.Optional;

public interface CompanyRepository {

    void save(Company company);

    Optional<Company> findById(Long id);

    Optional<Company> findByText(String name);

    void deleteById(Long id);

}
