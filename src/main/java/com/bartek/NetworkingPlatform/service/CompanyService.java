package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.company.CompanyCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.company.CompanyUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.company.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyResponse createCompany(CompanyCreateRequest request);
    CompanyResponse getCompanyById(Long companyId);
    CompanyResponse updateCompany(Long companyId, CompanyUpdateRequest request);
    void deleteCompany(Long companyId);
    Page<CompanyResponse> getAllCompanies(Pageable pageable);
    Page<CompanyResponse> searchCompaniesByName(String name, Pageable pageable);
    Page<CompanyResponse> searchCompaniesByLocation(String location, Pageable pageable);
    Page<CompanyResponse> searchCompanies(String name, String location, Pageable pageable);
}
