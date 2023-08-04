package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.FeedBackFromEmployee;
import com.yam.ecompany.repository.FeedBackFromEmployeeRepository;
import com.yam.ecompany.service.criteria.FeedBackFromEmployeeCriteria;
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
 * Service for executing complex queries for {@link FeedBackFromEmployee} entities in the database.
 * The main input is a {@link FeedBackFromEmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedBackFromEmployee} or a {@link Page} of {@link FeedBackFromEmployee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedBackFromEmployeeQueryService extends QueryService<FeedBackFromEmployee> {

    private final Logger log = LoggerFactory.getLogger(FeedBackFromEmployeeQueryService.class);

    private final FeedBackFromEmployeeRepository feedBackFromEmployeeRepository;

    public FeedBackFromEmployeeQueryService(FeedBackFromEmployeeRepository feedBackFromEmployeeRepository) {
        this.feedBackFromEmployeeRepository = feedBackFromEmployeeRepository;
    }

    /**
     * Return a {@link List} of {@link FeedBackFromEmployee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedBackFromEmployee> findByCriteria(FeedBackFromEmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedBackFromEmployee> specification = createSpecification(criteria);
        return feedBackFromEmployeeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeedBackFromEmployee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedBackFromEmployee> findByCriteria(FeedBackFromEmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedBackFromEmployee> specification = createSpecification(criteria);
        return feedBackFromEmployeeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedBackFromEmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedBackFromEmployee> specification = createSpecification(criteria);
        return feedBackFromEmployeeRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedBackFromEmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedBackFromEmployee> createSpecification(FeedBackFromEmployeeCriteria criteria) {
        Specification<FeedBackFromEmployee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedBackFromEmployee_.id));
            }
            if (criteria.getRefContractPONumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getRefContractPONumber(), FeedBackFromEmployee_.refContractPONumber)
                    );
            }
            if (criteria.getFeedBackCategory() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getFeedBackCategory(), FeedBackFromEmployee_.feedBackCategory));
            }
            if (criteria.getVendorsNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendorsNameId(),
                            root -> root.join(FeedBackFromEmployee_.vendorsName, JoinType.LEFT).get(Vendor_.id)
                        )
                    );
            }
            if (criteria.getProductNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductNameId(),
                            root -> root.join(FeedBackFromEmployee_.productName, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
