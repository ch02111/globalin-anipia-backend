package com.afivestudio.anipia.company.query;

import com.afivestudio.anipia.company.query.dto.CompanyDto;
import com.afivestudio.anipia.company.query.dto.CompanySearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface CompanyQueryMapper {

    List<CompanyDto> search(
            @Param("condition") CompanySearchCondition condition,
            @Param("pageable") Pageable pageable);

    long count(@Param("condition") CompanySearchCondition condition);
}
