package com.afivestudio.anipia.company.infra;

import com.afivestudio.anipia.company.domain.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CompanyMapper {
    void insert(Company company);

    void update(Company company);

    void deleteById(Long id);

    Optional<Company> findById(Long id);

    Optional<Company> findByText(String name);
}

