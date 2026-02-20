package com.afivestudio.anipia.company.infra;

import com.afivestudio.anipia.company.domain.Company;
import com.afivestudio.anipia.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisCompanyRepository implements CompanyRepository {
    private final CompanyMapper mapper;

    @Override
    public void save(Company company) {
        if (company.getId() == null) {
            mapper.insert(company);
        } else {
            mapper.update(company);
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Optional<Company> findByText(String name) {
        return mapper.findByText(name);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }
}

