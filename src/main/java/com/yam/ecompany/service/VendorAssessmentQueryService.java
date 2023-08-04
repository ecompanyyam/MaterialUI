package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.VendorAssessment;
import com.yam.ecompany.repository.VendorAssessmentRepository;
import com.yam.ecompany.service.criteria.VendorAssessmentCriteria;
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
 * Service for executing complex queries for {@link VendorAssessment} entities in the database.
 * The main input is a {@link VendorAssessmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendorAssessment} or a {@link Page} of {@link VendorAssessment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorAssessmentQueryService extends QueryService<VendorAssessment> {

    private final Logger log = LoggerFactory.getLogger(VendorAssessmentQueryService.class);

    private final VendorAssessmentRepository vendorAssessmentRepository;

    public VendorAssessmentQueryService(VendorAssessmentRepository vendorAssessmentRepository) {
        this.vendorAssessmentRepository = vendorAssessmentRepository;
    }

    /**
     * Return a {@link List} of {@link VendorAssessment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendorAssessment> findByCriteria(VendorAssessmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VendorAssessment> specification = createSpecification(criteria);
        return vendorAssessmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link VendorAssessment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorAssessment> findByCriteria(VendorAssessmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VendorAssessment> specification = createSpecification(criteria);
        return vendorAssessmentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorAssessmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VendorAssessment> specification = createSpecification(criteria);
        return vendorAssessmentRepository.count(specification);
    }

    /**
     * Function to convert {@link VendorAssessmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VendorAssessment> createSpecification(VendorAssessmentCriteria criteria) {
        Specification<VendorAssessment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VendorAssessment_.id));
            }
            if (criteria.getAssessmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssessmentDate(), VendorAssessment_.assessmentDate));
            }
            if (criteria.getAssessedBY() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssessedBY(), VendorAssessment_.assessedBY));
            }
            if (criteria.getJobKnowledge() != null) {
                specification = specification.and(buildSpecification(criteria.getJobKnowledge(), VendorAssessment_.jobKnowledge));
            }
            if (criteria.getWorkQuality() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkQuality(), VendorAssessment_.workQuality));
            }
            if (criteria.getAttendancePunctuality() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getAttendancePunctuality(), VendorAssessment_.attendancePunctuality));
            }
            if (criteria.getInitiative() != null) {
                specification = specification.and(buildSpecification(criteria.getInitiative(), VendorAssessment_.initiative));
            }
            if (criteria.getCommunicationListeningSkills() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCommunicationListeningSkills(), VendorAssessment_.communicationListeningSkills)
                    );
            }
            if (criteria.getDependability() != null) {
                specification = specification.and(buildSpecification(criteria.getDependability(), VendorAssessment_.dependability));
            }
            if (criteria.getVendorsNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendorsNameId(),
                            root -> root.join(VendorAssessment_.vendorsName, JoinType.LEFT).get(Vendor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
