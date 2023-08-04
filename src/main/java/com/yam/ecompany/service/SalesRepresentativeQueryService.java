package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.SalesRepresentative;
import com.yam.ecompany.repository.SalesRepresentativeRepository;
import com.yam.ecompany.service.criteria.SalesRepresentativeCriteria;
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
 * Service for executing complex queries for {@link SalesRepresentative} entities in the database.
 * The main input is a {@link SalesRepresentativeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalesRepresentative} or a {@link Page} of {@link SalesRepresentative} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesRepresentativeQueryService extends QueryService<SalesRepresentative> {

    private final Logger log = LoggerFactory.getLogger(SalesRepresentativeQueryService.class);

    private final SalesRepresentativeRepository salesRepresentativeRepository;

    public SalesRepresentativeQueryService(SalesRepresentativeRepository salesRepresentativeRepository) {
        this.salesRepresentativeRepository = salesRepresentativeRepository;
    }

    /**
     * Return a {@link List} of {@link SalesRepresentative} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalesRepresentative> findByCriteria(SalesRepresentativeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SalesRepresentative> specification = createSpecification(criteria);
        return salesRepresentativeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SalesRepresentative} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesRepresentative> findByCriteria(SalesRepresentativeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesRepresentative> specification = createSpecification(criteria);
        return salesRepresentativeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesRepresentativeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SalesRepresentative> specification = createSpecification(criteria);
        return salesRepresentativeRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesRepresentativeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesRepresentative> createSpecification(SalesRepresentativeCriteria criteria) {
        Specification<SalesRepresentative> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesRepresentative_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), SalesRepresentative_.fullName));
            }
            if (criteria.getJobTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobTitle(), SalesRepresentative_.jobTitle));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), SalesRepresentative_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), SalesRepresentative_.phone));
            }
            if (criteria.getOfficeLocation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOfficeLocation(), SalesRepresentative_.officeLocation));
            }
            if (criteria.getVendorsNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendorsNameId(),
                            root -> root.join(SalesRepresentative_.vendorsName, JoinType.LEFT).get(Vendor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
