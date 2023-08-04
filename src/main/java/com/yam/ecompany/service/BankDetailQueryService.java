package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.BankDetail;
import com.yam.ecompany.repository.BankDetailRepository;
import com.yam.ecompany.service.criteria.BankDetailCriteria;
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
 * Service for executing complex queries for {@link BankDetail} entities in the database.
 * The main input is a {@link BankDetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankDetail} or a {@link Page} of {@link BankDetail} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankDetailQueryService extends QueryService<BankDetail> {

    private final Logger log = LoggerFactory.getLogger(BankDetailQueryService.class);

    private final BankDetailRepository bankDetailRepository;

    public BankDetailQueryService(BankDetailRepository bankDetailRepository) {
        this.bankDetailRepository = bankDetailRepository;
    }

    /**
     * Return a {@link List} of {@link BankDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankDetail> findByCriteria(BankDetailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BankDetail> specification = createSpecification(criteria);
        return bankDetailRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BankDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDetail> findByCriteria(BankDetailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BankDetail> specification = createSpecification(criteria);
        return bankDetailRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankDetailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BankDetail> specification = createSpecification(criteria);
        return bankDetailRepository.count(specification);
    }

    /**
     * Function to convert {@link BankDetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BankDetail> createSpecification(BankDetailCriteria criteria) {
        Specification<BankDetail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BankDetail_.id));
            }
            if (criteria.getBankAccount() != null) {
                specification = specification.and(buildSpecification(criteria.getBankAccount(), BankDetail_.bankAccount));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), BankDetail_.bankName));
            }
            if (criteria.getBranchSwiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchSwiftCode(), BankDetail_.branchSwiftCode));
            }
            if (criteria.getIbanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIbanNo(), BankDetail_.ibanNo));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), BankDetail_.accountNumber));
            }
            if (criteria.getVendorsNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendorsNameId(),
                            root -> root.join(BankDetail_.vendorsName, JoinType.LEFT).get(Vendor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
