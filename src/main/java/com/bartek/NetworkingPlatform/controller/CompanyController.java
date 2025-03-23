package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.company.CompanyCreateRequest;
import com.bartek.NetworkingPlatform.dto.request.company.CompanyUpdateRequest;
import com.bartek.NetworkingPlatform.dto.response.company.CompanyResponse;
import com.bartek.NetworkingPlatform.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyCreateRequest request
    ) {
        CompanyResponse company = companyService.createCompany(request);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long companyId
    ) {
        CompanyResponse company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyUpdateRequest request
    ) {

        CompanyResponse company = companyService.updateCompany(companyId, request);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId
    ) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(pageable);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyResponse>> searchCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        Page<CompanyResponse> companies = companyService.searchCompanies(name, location, pageable);
        return ResponseEntity.ok(companies);
    }
}