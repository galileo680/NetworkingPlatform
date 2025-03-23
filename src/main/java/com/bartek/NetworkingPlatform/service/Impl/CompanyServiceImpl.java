package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.company.CompanyCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.company.CompanyUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.company.CompanyResponse;
import com.bartek.NetworkingPlatform.entity.Company;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.exception.ResourceAlreadyExistsException;
import com.bartek.NetworkingPlatform.mapper.CompanyMapper;
import com.bartek.NetworkingPlatform.repository.CompanyRepository;
import com.bartek.NetworkingPlatform.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyResponse createCompany(CompanyCreateRequest request) {
        if (companyRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Company named: " + request.getName() + " already exists");
        }

        Company company = Company.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .location(request.getLocation())
                .logoUrl(request.getLogoUrl())
                .build();

        Company savedCompany = companyRepository.save(company);
        return companyMapper.mapToCompanyResponse(savedCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long companyId) {
        Company company = findCompanyOrThrow(companyId);
        return companyMapper.mapToCompanyResponse(company);
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long companyId, CompanyUpdateRequest request) {
        Company company = findCompanyOrThrow(companyId);

        if (!company.getName().equals(request.getName()) && companyRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Company named: " + request.getName() + " already exists");
        }

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setLogoUrl(request.getLogoUrl());

        Company updatedCompany = companyRepository.save(company);
        return companyMapper.mapToCompanyResponse(updatedCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundException("Company with ID: " + companyId + " not found");
        }

        companyRepository.deleteById(companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(companyMapper::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> searchCompaniesByName(String name, Pageable pageable) {
        Page<Company> companies = companyRepository.findByNameContainingIgnoreCase(name, pageable);
        return companies.map(companyMapper::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> searchCompaniesByLocation(String location, Pageable pageable) {
        Page<Company> companies = companyRepository.findByLocationContainingIgnoreCase(location, pageable);
        return companies.map(companyMapper::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> searchCompanies(String name, String location, Pageable pageable) {
        Page<Company> companies;

        if (name != null && !name.isEmpty() && location != null && !location.isEmpty()) {
            companies = companyRepository.findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location, pageable);
        } else if (name != null && !name.isEmpty()) {
            companies = companyRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (location != null && !location.isEmpty()) {
            companies = companyRepository.findByLocationContainingIgnoreCase(location, pageable);
        } else {
            companies = companyRepository.findAll(pageable);
        }

        return companies.map(companyMapper::mapToCompanyResponse);
    }

    //Helper methods
    private Company findCompanyOrThrow(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with ID: " + companyId + " not found"));
    }

}