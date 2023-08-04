package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.repository.VendorRepository;
import com.yam.ecompany.service.criteria.VendorCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Vendor} entities in the database.
 * The main input is a {@link VendorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Vendor} or a {@link Page} of {@link Vendor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorQueryService extends QueryService<Vendor> {

    private final Logger log = LoggerFactory.getLogger(VendorQueryService.class);

    private final VendorRepository vendorRepository;

    public VendorQueryService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**
     * Return a {@link List} of {@link Vendor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Vendor> findByCriteria(VendorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Vendor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Vendor> findByCriteria(VendorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.count(specification);
    }

    /**
     * Function to convert {@link VendorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vendor> createSpecification(VendorCriteria criteria) {
        Specification<Vendor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vendor_.id));
            }
            if (criteria.getVendorNameEnglish() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorNameEnglish(), Vendor_.vendorNameEnglish));
            }
            if (criteria.getVendorNameArabic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorNameArabic(), Vendor_.vendorNameArabic));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorId(), Vendor_.vendorId));
            }
            if (criteria.getVendorAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorAccountNumber(), Vendor_.vendorAccountNumber));
            }
            if (criteria.getVendorCRNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorCRNumber(), Vendor_.vendorCRNumber));
            }
            if (criteria.getVendorVATNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorVATNumber(), Vendor_.vendorVATNumber));
            }
            if (criteria.getVendorType() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorType(), Vendor_.vendorType));
            }
            if (criteria.getVendorCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorCategory(), Vendor_.vendorCategory));
            }
            if (criteria.getVendorEstablishmentDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVendorEstablishmentDate(), Vendor_.vendorEstablishmentDate));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Vendor_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Vendor_.width));
            }
            if (criteria.getTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaken(), Vendor_.taken));
            }
            if (criteria.getUploaded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploaded(), Vendor_.uploaded));
            }
            if (criteria.getContactFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactFullName(), Vendor_.contactFullName));
            }
            if (criteria.getContactEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmailAddress(), Vendor_.contactEmailAddress));
            }
            if (criteria.getContactPrimaryPhoneNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactPrimaryPhoneNo(), Vendor_.contactPrimaryPhoneNo));
            }
            if (criteria.getContactPrimaryFaxNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPrimaryFaxNo(), Vendor_.contactPrimaryFaxNo));
            }
            if (criteria.getContactSecondaryPhoneNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactSecondaryPhoneNo(), Vendor_.contactSecondaryPhoneNo));
            }
            if (criteria.getContactSecondaryFaxNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactSecondaryFaxNo(), Vendor_.contactSecondaryFaxNo));
            }
            if (criteria.getOfficeLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOfficeLocation(), Vendor_.officeLocation));
            }
            if (criteria.getOfficeFunctionality() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeFunctionality(), Vendor_.officeFunctionality));
            }
            if (criteria.getWebsiteURL() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsiteURL(), Vendor_.websiteURL));
            }
            if (criteria.getBuildingNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuildingNo(), Vendor_.buildingNo));
            }
            if (criteria.getVendorStreetName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorStreetName(), Vendor_.vendorStreetName));
            }
            if (criteria.getZipCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZipCode(), Vendor_.zipCode));
            }
            if (criteria.getDistrictName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictName(), Vendor_.districtName));
            }
            if (criteria.getAdditionalNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalNo(), Vendor_.additionalNo));
            }
            if (criteria.getCityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityName(), Vendor_.cityName));
            }
            if (criteria.getUnitNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitNo(), Vendor_.unitNo));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildSpecification(criteria.getCountry(), Vendor_.country));
            }
            if (criteria.getCash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCash(), Vendor_.cash));
            }
            if (criteria.getCredit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCredit(), Vendor_.credit));
            }
            if (criteria.getLetterOfCredit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLetterOfCredit(), Vendor_.letterOfCredit));
            }
            if (criteria.getOthers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOthers(), Vendor_.others));
            }
        }
        return specification;
    }
}
